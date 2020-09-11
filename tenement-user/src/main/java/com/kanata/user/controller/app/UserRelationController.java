package com.kanata.user.controller.app;

import com.kanata.core.dto.page.Page;
import com.kanata.core.entity.UserRelationEntity;
import com.kanata.user.controller.api.userRelation.RequestUserRelationFilterGet;
import com.kanata.user.controller.api.userRelation.RequestUserRelationPost;
import com.kanata.user.controller.api.userRelation.ResponseUserFriendGet;
import com.kanata.user.controller.api.userRelation.ResponseUserRelationGet;
import com.kanata.user.dao.app.UserRelationRepo;
import com.kanata.user.service.app.UserRelationService;
import com.kanata.user.service.app.bo.userRelation.UserRelationBo;
import com.kanata.user.service.app.bo.userRelation.UserRelationFilterBo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/app/user/relation")
@RestController
@Api(tags = "用户关系模块")
public class UserRelationController {
    @Autowired
    private UserRelationService userRelationService;

    @Autowired
    private UserRelationRepo userRelationRepo;

    @ApiOperation(value = "加好友")
    @RequestMapping(value = "/save/friends",method = RequestMethod.POST)
    public void saveFriends(@RequestBody RequestUserRelationPost request){
        UserRelationBo userBo = new UserRelationBo();
        BeanUtils.copyProperties(request,userBo);
        if(request.getType()==0||request.getType()==-1){
            userRelationService.createFriend(userBo);
        }else if(request.getType()==1){
            UserRelationBo friendBo = new UserRelationBo();
            friendBo.setUserid(request.getFriendId());
            friendBo.setFriendId(request.getUserid());
            friendBo.setType(request.getType());
            userRelationService.createFriend(userBo);
            userRelationService.createFriend(friendBo);
        }

    }

    @ApiOperation(value = "找好友")
    @RequestMapping(value = "/find/friends",method = RequestMethod.GET)

    public ResponseUserFriendGet find(RequestUserRelationFilterGet req){
        UserRelationFilterBo userRelationFilterBo = new UserRelationFilterBo();
        BeanUtils.copyProperties(req,userRelationFilterBo);
        Page page = userRelationService.findFriend(userRelationFilterBo);
        ResponseUserFriendGet response = new ResponseUserFriendGet();
        List<ResponseUserFriendGet.User> users = new ArrayList<>();
        page.getData().forEach(e->{
            ResponseUserFriendGet.User user = new ResponseUserFriendGet.User();
            BeanUtils.copyProperties(e,user);
            users.add(user);
        });
        response.setUsers(users);
        response.setTotal(users.size());
        return response;
    }

    @ApiOperation(value = "通过用户ID和好友ID查找关系")
    @GetMapping(value = "/{userId}")
    public ResponseUserRelationGet findRelation(@PathVariable int userId,int friendId){
        UserRelationEntity userRelationEntity = userRelationRepo.findByUseridAndFriendId(userId,friendId);
        ResponseUserRelationGet responseUserRelationGet = new ResponseUserRelationGet();
        BeanUtils.copyProperties(userRelationEntity,responseUserRelationGet);
        return responseUserRelationGet;
    }
}
