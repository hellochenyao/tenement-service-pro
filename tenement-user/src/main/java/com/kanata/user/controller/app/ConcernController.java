package com.kanata.user.controller.app;

import com.kanata.core.common.enums.ConcernType;
import com.kanata.core.dto.page.Page;
import com.kanata.core.entity.ConcernEntity;
import com.kanata.core.entity.UserInfoEntity;
import com.kanata.core.exception.BusinessException;
import com.kanata.core.util.DateUtils;
import com.kanata.user.controller.api.concern.RequestConcernUsersFilterGet;
import com.kanata.user.controller.api.concern.ResponseConcernGet;
import com.kanata.user.controller.api.concern.ResponseConcernInfoGet;
import com.kanata.user.controller.api.concern.ResponseConcernUsersListGet;
import com.kanata.user.service.app.ConcernService;
import com.kanata.user.service.app.bo.concern.ConcernFilterBo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Api(tags = "用户关注模块")
@RequestMapping(value = "/app/user/concern/{userId}")
@RestController
public class ConcernController {

    @Autowired
    private ConcernService concernService;

    @ApiOperation(value = "关注用户和圈子或取关")
    @RequestMapping(value="/save/record/{type}/{toUserid}",method = RequestMethod.POST)
    public void saveConcern(@PathVariable("toUserid") int toUserid,
                            @PathVariable("userId") int userid,
                            @PathVariable("type") int type,
                            @RequestParam ConcernType concernType
                            ){
        if(type==0){
            if(userid==toUserid&&ConcernType.USER.equals(concernType)){
                throw new BusinessException("CONCERN_SELF","不能关注自己");
            }
            concernService.saveUserConcern(userid,toUserid,concernType);
        }else{
            concernService.deletedUserConcern(userid,toUserid,concernType);
        }
    }

    @ApiOperation(value = "查询某用户的关注数和粉丝数")
    @RequestMapping(value = "/find/nums",method = RequestMethod.GET)
    public ResponseConcernGet queryNums(@ApiParam(value = "用户ID") @RequestParam int toUserid,
                                        @RequestParam ConcernType concernType
    ){
        ResponseConcernGet response = new ResponseConcernGet();
        int concernNums = concernService.queryConcernNums(toUserid,concernType);
        int admireNums = concernService.queryAdmiresNums(toUserid,concernType);
        response.setConcernNums(concernNums);
        response.setAdmireNums(admireNums);
        return response;
    }

    @ApiOperation(value="某用户的关注列表或粉丝列表")
    @RequestMapping(value = "find/concern/list/{type}",method = RequestMethod.GET)
    public ResponseConcernUsersListGet findConcernUsers(RequestConcernUsersFilterGet req,
                                                        @PathVariable("type") int type,
                                                        @PathVariable("userId") int userId,
                                                        @RequestParam ConcernType concernType){
        ConcernFilterBo concernFilterBo = new ConcernFilterBo();
        BeanUtils.copyProperties(req,concernFilterBo);
        concernFilterBo.setType(type);
        concernFilterBo.setUserid(userId);
        concernFilterBo.setConcernType(concernType);
        Page<UserInfoEntity> page = concernService.findConcernList(concernFilterBo);
        List<ResponseConcernUsersListGet.User> list = new ArrayList<>();
        page.getData().forEach(e->{
            ResponseConcernUsersListGet.User user = new ResponseConcernUsersListGet.User();
            BeanUtils.copyProperties(e,user);
            user.setUserid(e.getId());
            user.setLastLoginTime(DateUtils.getLocalDateTimeStr(e.getLastLoginTime()));
            list.add(user);
        });
        ResponseConcernUsersListGet response = new ResponseConcernUsersListGet();
        response.setData(list);
        response.setTotal(page.getTotal());
        return response;
    }

    @ApiOperation(value="是否有关注改用户")
    @RequestMapping(value="find/concern/state",method = RequestMethod.GET)
    public Integer findConcernState(@ApiParam(value = "关注用户ID") @RequestParam int toUserid,
                                    @PathVariable("userId") int userId,
                                    @RequestParam ConcernType concernType
    ){
        return concernService.findIsConcern(userId,toUserid,concernType);
    }

    @ApiOperation(value = "查找某用户的粉丝")
    @GetMapping(value = "/find/user/fans")
    public ResponseConcernInfoGet findConcerns(@ApiParam("被关注的用户ID") @RequestParam int toUserid,ConcernType concernType
                                               ) throws InterruptedException {
        List<ConcernEntity> concernEntities = concernService.findConcerns(toUserid,concernType);
        List<ResponseConcernInfoGet.ConcernInfo> concernInfos = concernEntities.stream()
                .map(e->{
                    ResponseConcernInfoGet.ConcernInfo concernInfo = new ResponseConcernInfoGet.ConcernInfo();
                    BeanUtils.copyProperties(e,concernInfo);
                    return concernInfo;
                }).collect(Collectors.toList());
        ResponseConcernInfoGet response = new ResponseConcernInfoGet();
        response.setConcernInfoList(concernInfos);
        return response;
    }
}
