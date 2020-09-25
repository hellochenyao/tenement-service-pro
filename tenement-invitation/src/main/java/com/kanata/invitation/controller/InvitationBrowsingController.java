package com.kanata.invitation.controller;


import com.kanata.core.dto.page.Page;
import com.kanata.core.entity.TenementInvitationEntity;
import com.kanata.core.util.DateUtils;
import com.kanata.feign.api.message.IMessageFeignClient;
import com.kanata.feign.api.user.IUserInfoFeignClient;
import com.kanata.feign.dto.UserInfoDto;
import com.kanata.invitation.controller.api.invitation.*;
import com.kanata.invitation.controller.api.userBrowsing.RequestUserBrowsingFilterGet;
import com.kanata.invitation.dao.vo.tenementInvitation.InvitationUserInfoVo;
import com.kanata.invitation.service.InvitationBrowsingService;
import com.kanata.invitation.service.TenementInvitationService;
import com.kanata.invitation.service.UserLikeService;
import com.kanata.invitation.service.bo.invitationBrowsing.InvitationBrowsingBo;
import com.kanata.invitation.service.bo.invitationBrowsing.UserBrowsingFilterBo;
import com.kanata.invitation.service.bo.tenementInvitation.InvitationUserInfoBo;
import com.kanata.invitation.service.bo.tenementInvitation.TenementInvitationFilterBo;
import com.kanata.invitation.service.bo.userLike.UserLikeBo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zuzu on 2019/5/9.
 */

@RestController
@Api(tags = "App-租房帖子浏览模块")
@RequestMapping("/app/tenement/{userId}")
@Slf4j
public class InvitationBrowsingController {

    @Autowired
    private TenementInvitationService tenementInvitationService;

    @Autowired
    private InvitationBrowsingService invitationBrowsingService;

    @Autowired
    private IMessageFeignClient messageFeignClient;

    @Autowired
    private IUserInfoFeignClient userInfoFeignClient;

    @Autowired
    private UserLikeService userLikeService;


    @ApiOperation("根据类型查找帖子")
    @RequestMapping(value = "/invitations", method = RequestMethod.GET)
    public ResponseTenementInvitationGet queryPublishedInvitation(RequestTenementInvitationGet request, @PathVariable("userId") Integer userId) {
        TenementInvitationFilterBo tenementInvitationFilterBo = new TenementInvitationFilterBo();
        BeanUtils.copyProperties(request, tenementInvitationFilterBo);

        Page<TenementInvitationEntity> page = tenementInvitationService.findInvitations(tenementInvitationFilterBo);
        ResponseTenementInvitationGet response = new ResponseTenementInvitationGet();
        List<ResponseTenementInvitationGet.TenementInvitation> list = new ArrayList<>();
        page.getData().forEach(e -> {
            ResponseTenementInvitationGet.TenementInvitation tenementInvitation = new ResponseTenementInvitationGet.TenementInvitation();
            Integer msgNums = messageFeignClient.findResponseMsgNums(e.getId());
            if(msgNums == -1){
                log.error("通过帖子ID查找回复消息失败,invitationId:{}",e.getId());
            }
            UserLikeBo userLikeBo = userLikeService.findUserLikeByInvitationIdAndUserId(e.getId(),userId);
            BeanUtils.copyProperties(e, tenementInvitation);
            if(userLikeBo!=null){
                tenementInvitation.setLikeStatus(userLikeBo.getStatus());
            }
            tenementInvitation.setCreateTime(DateUtils.getLocalDateTimeStr(e.getCreateTime()));
            tenementInvitation.setUpdateTime(DateUtils.getLocalDateTimeStr(e.getUpdateTime()));
            tenementInvitation.setDesiredDate(DateUtils.getLocalDateStr(e.getDesiredDate()));
            UserInfoDto userInfoDto = userInfoFeignClient.findUserInfo(e.getUserId());
            tenementInvitation.setLastLoginTime(userInfoDto.getLastLoginTime());
            tenementInvitation.setPublisher(e.getPublisher());
            tenementInvitation.setAvatar(userInfoDto.getAvatar());
            tenementInvitation.setGender(userInfoDto.getGender());
            tenementInvitation.setLeaveMsgNums(msgNums);
            list.add(tenementInvitation);
        });
        response.setData(list);
        response.setTotal(page.getTotal());
        return response;
    }



    @ApiOperation("查看帖子详情 更新帖子浏览次数")
    @RequestMapping(value = "/detail/{invitationId}", method = RequestMethod.PUT)
    public void detail(@PathVariable("invitationId") int invitationId, @PathVariable("userId") Integer userId) {
        InvitationBrowsingBo invitationBrowsingBo = new InvitationBrowsingBo();
        invitationBrowsingBo.setUserId(userId);
        invitationBrowsingBo.setInvitationId(invitationId);
        invitationBrowsingBo.setBrowsingTime(LocalDateTime.now());
        invitationBrowsingService.viewDetail(invitationBrowsingBo);
    }

    @ApiOperation("举报帖子")
    @RequestMapping(value = "/tip-offs/{invitationId}", method = RequestMethod.POST)
    public void  tipOffs(int invitationId) {

    }

    @ApiOperation(value = "根据指定id获得帖子信息")
    @RequestMapping(value = "/find/invitation/{id}",method = RequestMethod.GET)
    public ResponseInvitationGet find(@PathVariable("id") int id){
      InvitationUserInfoBo invitationUserInfoBo = invitationBrowsingService.findByInvitation(id);
      ResponseInvitationGet response = new ResponseInvitationGet();
      BeanUtils.copyProperties(invitationUserInfoBo,response);
      return response;
    }

    @ApiOperation(value = "增加浏览记录")
    @RequestMapping(value = "/add/browsing/record/{invitationid}",method = RequestMethod.POST)
    public void addBrowsingRecord(@PathVariable int userId , @PathVariable int invitationid){
        invitationBrowsingService.addBrowsingRecord(userId,invitationid);
    }

    @ApiOperation(value = "分页查询用户的浏览记录")
    @RequestMapping(value = "/query/browsing/records",method = RequestMethod.GET)
    public ResponseTenementInvitationGet queryUserBrowsingRecords(RequestUserBrowsingFilterGet request){
        UserBrowsingFilterBo filterBo = new UserBrowsingFilterBo();
        BeanUtils.copyProperties(request,filterBo);
        ResponseTenementInvitationGet response = new ResponseTenementInvitationGet();
        Page<InvitationUserInfoVo> page = invitationBrowsingService.findBrowsingInvitation(filterBo);
        List<ResponseTenementInvitationGet.TenementInvitation> list = new ArrayList<>();
        page.getData().forEach(e->{
            ResponseTenementInvitationGet.TenementInvitation tenementInvitation = new ResponseTenementInvitationGet.TenementInvitation();
            BeanUtils.copyProperties(e,tenementInvitation);
            tenementInvitation.setCreateTime(DateUtils.getLocalDateTimeStr(e.getCreateTime()));
            tenementInvitation.setUpdateTime(DateUtils.getLocalDateTimeStr(e.getUpdateTime()));
            tenementInvitation.setDesiredDate(DateUtils.getLocalDateTimeStr(e.getDesiredDate()));
            tenementInvitation.setLastLoginTime(DateUtils.getLocalDateTimeStr(e.getLastLoginTime()));
            list.add(tenementInvitation);
        });
        response.setData(list);
        response.setTotal(page.getTotal());
        return response;
    }

}
