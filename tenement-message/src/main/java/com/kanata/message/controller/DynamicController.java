package com.kanata.message.controller;

import com.kanata.core.entity.CommentEntity;
import com.kanata.core.entity.DynamicEntity;
import com.kanata.feign.api.user.IUserInfoFeignClient;
import com.kanata.feign.dto.UserInfoDto;
import com.kanata.message.controller.api.dynamic.RequestCreateDynamicPost;
import com.kanata.message.controller.api.dynamic.RequestDynamicFilterGet;
import com.kanata.message.controller.api.dynamic.ResponseDynamicGet;
import com.kanata.message.dao.CommentRepo;
import com.kanata.message.dao.LikeDynamicRepo;
import com.kanata.message.service.DynamicService;
import com.kanata.message.service.bo.dynamic.DynamicBo;
import com.kanata.message.service.bo.dynamic.DynamicFilterBo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Api(tags = "APP动态模块")
@RequestMapping(value = "/app/user/dynamic/{userId}")
public class DynamicController {
    @Autowired
    private DynamicService dynamicService;

    @Autowired
    private IUserInfoFeignClient userInfoFeignClient;

    @Autowired
    private LikeDynamicRepo likeDynamicRepo;

    @Autowired
    private CommentRepo commentRepo;

    @ApiOperation(value = "用户发动态")
    @PostMapping(value = "/create/dynamic")
    public void createDynamic(@RequestBody RequestCreateDynamicPost request){
        DynamicBo dynamicBo = new DynamicBo();
        BeanUtils.copyProperties(request,dynamicBo);
        dynamicService.createDynamic(dynamicBo);
    }

    @ApiOperation(value = "用户查动态")
    @GetMapping(value = "/query/dynamic")
    public ResponseDynamicGet queryDynamic(RequestDynamicFilterGet request, @PathVariable int userId){
        DynamicFilterBo dynamicFilterBo = new DynamicFilterBo();
        BeanUtils.copyProperties(request,dynamicFilterBo);
        dynamicFilterBo.setUserId(userId);
        ResponseDynamicGet responseDynamicGet = new ResponseDynamicGet();
        org.springframework.data.domain.Page<DynamicEntity> pageData = dynamicService.query(dynamicFilterBo);
        List<ResponseDynamicGet.Dynamic> dynamicV  = pageData.stream().map(e->{
            ResponseDynamicGet.Dynamic dynamic = new ResponseDynamicGet.Dynamic();
            BeanUtils.copyProperties(e,dynamic);
            UserInfoDto userInfoDto = userInfoFeignClient.findUserInfo(e.getUserId());
            dynamic.setUserName(userInfoDto.getNickName());
            dynamic.setAvatar(userInfoDto.getAvatar());
            List<CommentEntity> commentEntities = commentRepo.findByDynamicId(e.getId());
            dynamic.setReplyNums(commentEntities.size());
            return dynamic;
        }).collect(Collectors.toList());
        responseDynamicGet.setData(dynamicV);
        responseDynamicGet.setTotal((int)pageData.getTotalElements());
        return responseDynamicGet;
    }
}
