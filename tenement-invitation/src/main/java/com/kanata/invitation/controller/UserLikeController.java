package com.kanata.invitation.controller;

import com.kanata.core.dto.page.Page;
import com.kanata.core.entity.UserLikeEntity;
import com.kanata.core.exception.BusinessException;
import com.kanata.core.util.DateUtils;
import com.kanata.feign.api.user.IUserInfoFeignClient;
import com.kanata.feign.dto.UserInfoDto;
import com.kanata.invitation.controller.api.userLike.*;
import com.kanata.invitation.dao.vo.userLike.GiveUserLikeVo;
import com.kanata.invitation.service.UserLikeService;
import com.kanata.invitation.service.bo.userLike.GiveUserLikeFilterBo;
import com.kanata.invitation.service.bo.userLike.UserLikeBo;
import com.kanata.invitation.service.bo.userLike.UserLikeFilterBo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@Api(tags = "用户点赞帖子模块")
@RequestMapping("/app/tenement/{userId}/like")
public class UserLikeController {

    @Autowired
    UserLikeService userLikeService;

    @Autowired
    private IUserInfoFeignClient userInfoFeignClient;

    @ApiOperation("点赞或踩帖子")
    @RequestMapping(value = "/give/invitation",method = RequestMethod.POST)
    public void saveUserLikeStatus(@RequestBody RequestLikeStatusPost request){
        UserLikeBo userLikeBo = new UserLikeBo();
        BeanUtils.copyProperties(request,userLikeBo);
        userLikeService.updateInvitationSupportNums(userLikeBo.getLikeInvitationId(),userLikeBo.getLikeUserId(),userLikeBo.getStatus());
    }

    @ApiOperation("帖子ID查找所有点赞或踩的用户")
    @RequestMapping(value = "/find/opt/user",method = RequestMethod.GET)
    public ResponseLikeUsersGet findLikeUsers(RequestLikeUsersFilterGet request){
        UserLikeFilterBo userLikeFilterBo = new UserLikeFilterBo();
        ResponseLikeUsersGet response = new ResponseLikeUsersGet();
        BeanUtils.copyProperties(request,userLikeFilterBo);
        Page<UserLikeEntity> page = userLikeService.getUserLikeListByInvitationId(userLikeFilterBo);
        List<ResponseLikeUsersGet.LikeUser> list = new ArrayList<>();
        try{
            page.getData().forEach(e->{
                ResponseLikeUsersGet.LikeUser likeUser = new ResponseLikeUsersGet.LikeUser();
                likeUser.setLikeUserId(e.getLikeUserId());
                UserInfoDto userInfoDto = userInfoFeignClient.findUserInfo(e.getLikeUserId());
                if(userInfoDto.getId() == -1){
                    throw new BusinessException("USER_NOT_EXIST", "用户不存在");
                }
                likeUser.setUserName(userInfoDto.getNickName());
                list.add(likeUser);
            });
        }catch (Exception e){
            e.printStackTrace();
        }
        response.setLikeUsers(list);
        response.setLikeInvitationId(request.getLikeInvitationId());
        return response;
    }

    @ApiOperation("根据用户id查找所有的点赞或踩操作帖子")
    @RequestMapping(value = "/find/opt/invitation",method = RequestMethod.GET)
    public ResponseLikeInvitationsGet findLikeInvitations(RequestLikeInvitationsFilterGet request){
        UserLikeFilterBo userLikeFilterBo = new UserLikeFilterBo();
        ResponseLikeInvitationsGet response = new ResponseLikeInvitationsGet();
        BeanUtils.copyProperties(request,userLikeFilterBo);
        Page<UserLikeEntity> page = userLikeService.getInvitationLikeListByUserId(userLikeFilterBo);
        List<ResponseLikeInvitationsGet.LikeInvitation> list = new ArrayList<>();
        page.getData().forEach(e->{
            ResponseLikeInvitationsGet.LikeInvitation likeInvitation = new ResponseLikeInvitationsGet.LikeInvitation();
            likeInvitation.setInvitationId(e.getLikeInvitationId());
            list.add(likeInvitation);
        });
        response.setLikeInvitations(list);
        response.setLikeUserId(request.getLikeUserId());
        return response;
    }

    @ApiOperation("根据用户id和帖子id查找")
    @RequestMapping(value = "/find/userLike",method = RequestMethod.GET)
    public ResponseUserLikeGet findUserLike(int invitationId, int userId){
        ResponseUserLikeGet response = new ResponseUserLikeGet();
        UserLikeBo userLikeBo = userLikeService.findUserLikeByInvitationIdAndUserId( invitationId,  userId);
        BeanUtils.copyProperties(userLikeBo,response);
        return response;
    }

    @ApiOperation("查找所有点赞我的帖子用户")
    @RequestMapping(value = "/find/like/invitation/users",method = RequestMethod.GET)
    public ResponseGiveLikeUsersGet findGiveLikeUsers(RequestGiveLikeFilterGet request){
        GiveUserLikeFilterBo filterBo = new GiveUserLikeFilterBo();
        BeanUtils.copyProperties(request,filterBo);
        Page<GiveUserLikeVo> page = userLikeService.getUserGiveLike(filterBo);
        List<ResponseGiveLikeUsersGet.UserInfo> list = new ArrayList<>();
        page.getData().forEach(e->{
            ResponseGiveLikeUsersGet.UserInfo userInfo = new ResponseGiveLikeUsersGet.UserInfo();
            BeanUtils.copyProperties(e,userInfo);
            userInfo.setUpdateTime(DateUtils.getLocalDateTimeStr(e.getUpdateTime()));
            list.add(userInfo);
        });
        ResponseGiveLikeUsersGet responseGiveLikeUsersGet = new ResponseGiveLikeUsersGet();
        responseGiveLikeUsersGet.setData(list);
        responseGiveLikeUsersGet.setTotal(page.getTotal());
        return responseGiveLikeUsersGet;
    }
}
