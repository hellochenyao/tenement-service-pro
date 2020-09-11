package com.kanata.message.controller;

import com.kanata.core.dto.page.Page;
import com.kanata.core.entity.UserMsgEntity;
import com.kanata.core.util.DateUtils;
import com.kanata.feign.api.invitation.IInvatitionFeignClient;
import com.kanata.feign.api.user.IUserInfoFeignClient;
import com.kanata.feign.dto.InvitationDto;
import com.kanata.feign.dto.UserInfoDto;
import com.kanata.message.controller.api.userMsg.*;
import com.kanata.message.service.UserMsgService;
import com.kanata.message.service.bo.userMsg.UserMsgBo;
import com.kanata.message.service.bo.userMsg.UserMsgFilterBo;
import com.kanata.message.service.bo.userMsg.UserMsgResponseBo;
import com.kanata.message.service.bo.userMsg.UserResponseMsgFilterBo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chenyao
 * date 2020-09-08
 */
@RestController
@Api(tags = "App-消息模块")
@RequestMapping(value = "/app/message")
public class UserMsgController {

    @Autowired
    private UserMsgService userMsgService;

    @Autowired
    private IUserInfoFeignClient userInfoFeignClient;

    @Autowired
    private IInvatitionFeignClient invatitionFeignClient;

    @ApiOperation(value = "获取帖子的回复数")
    @GetMapping(value = "/nums/{invitationId}")
    public Integer findMessageNums(@PathVariable int invitationId){
        return userMsgService.findLeaveMsgNumsByInvitationId(invitationId);
    }

    @ApiOperation("帖子下方留言")
    @RequestMapping(value = "/leave/words/{invitationId}/{userId}", method = RequestMethod.POST)
    public ResponseWordPost leaveWords(@RequestBody RequestLeaveWordsPost request, @PathVariable int invitationId, @PathVariable("userId") Integer userId) {
        UserMsgBo userMsgBo = new UserMsgBo();
        userMsgBo.setUserId(userId);
        userMsgBo.setInvitationId(invitationId);
        BeanUtils.copyProperties(request,userMsgBo);
        UserMsgEntity userMsg = userMsgService.leaveWord(userMsgBo);
        ResponseWordPost response = new ResponseWordPost();
        response.setMsgId(userMsg.getId());
        return response;
    }

    @ApiOperation("根据id按页数查找帖子的所有留言")
    @RequestMapping(value="/leave/words-items",method = RequestMethod.GET)
    public ResponseLeaveWordsGet findLeaveWord(RequestLeaveWordsFilterGet request){
        UserMsgFilterBo userMsgFilterBo = new UserMsgFilterBo();
        UserResponseMsgFilterBo userResponseMsgFilterBo = new UserResponseMsgFilterBo();
        BeanUtils.copyProperties(request,userMsgFilterBo);
        Page<UserMsgEntity> page = userMsgService.findLeaveWord(userMsgFilterBo);
        List<ResponseLeaveWordsGet.DetailWords> detailWordsList = new ArrayList<>();
        page.getData().forEach(e->{
            userResponseMsgFilterBo.setPid(e.getId());
            userResponseMsgFilterBo.setInvitationId(e.getInvitationId());
            userResponseMsgFilterBo.setPageNo(1);
            userResponseMsgFilterBo.setPageSize(5);
            UserInfoDto userInfoDto = userInfoFeignClient.findUserInfo(e.getUserId());
            Page<UserMsgEntity> responsePage = userMsgService.findResponseLeaveWords(userResponseMsgFilterBo);
            List<ResponseLeaveWordsGet.DetailWords.ResDetailWords> resDetailList = new ArrayList<>();
            responsePage.getData().forEach(resEntity->{
                ResponseLeaveWordsGet.DetailWords.ResDetailWords responseDetailWords = new ResponseLeaveWordsGet.DetailWords.ResDetailWords();
                responseDetailWords.setAnswerUserId(resEntity.getAnswerUserId());
                UserInfoDto userRe = userInfoFeignClient.findUserInfo(resEntity.getUserId());
                responseDetailWords.setMsg(resEntity.getMsg());
                responseDetailWords.setUserId(resEntity.getUserId());
                responseDetailWords.setAvatar(userRe.getAvatar());
                responseDetailWords.setLastLoginTime(userRe.getLastLoginTime());
                responseDetailWords.setGender(userRe.getGender());
                responseDetailWords.setNickname(resEntity.getNickname());
                responseDetailWords.setAnswerUserNickname(resEntity.getAnswerUserNickname());
                responseDetailWords.setCreateTime(DateUtils.getLocalDateTimeStr(resEntity.getCreateTime()));
                resDetailList.add(responseDetailWords);
            });
            ResponseLeaveWordsGet.DetailWords detailWords = new ResponseLeaveWordsGet.DetailWords();
            detailWords.setUserId(e.getUserId());
            detailWords.setCreateTime(DateUtils.getLocalDateTimeStr(e.getCreateTime()));
            detailWords.setId(e.getId());
            detailWords.setLastLoginTime(userInfoDto.getLastLoginTime());
            detailWords.setGender(userInfoDto.getGender());
            detailWords.setMsg(e.getMsg());
            detailWords.setNickname(e.getNickname());
            detailWords.setResTotal(responsePage.getTotal());
            detailWords.setResDetail(resDetailList);
            detailWords.setAvatar(userInfoDto.getAvatar());
            if(e.getPid()==0){
                detailWordsList.add(detailWords);
            }
        });
        ResponseLeaveWordsGet response = new ResponseLeaveWordsGet();
        response.setDetails(detailWordsList);
        response.setTotal(page.getTotal());
        return response;
    }

    @ApiOperation(value = "根据回复的id查找回复具体内容")
    @RequestMapping(value = "/find/main/response/msg/{id}/content",method = RequestMethod.GET)
    public ResponseUserMsgGet findMsg(@PathVariable("id") int msgId){
        ResponseUserMsgGet response = new ResponseUserMsgGet();
        UserMsgEntity userMsgEntity = userMsgService.getResponseMsgContent(msgId);
        UserInfoDto user = userInfoFeignClient.findUserInfo(userMsgEntity.getUserId());
        response.setAvatar(user.getAvatar());
        response.setGender(user.getGender());
        BeanUtils.copyProperties(userMsgEntity,response);
        response.setCreateTime(DateUtils.getLocalDateTimeStr(userMsgEntity.getCreateTime()));
        return response;
    }
    @ApiOperation(value = "根据pid找某个帖子留言的所有回复内容")
    @RequestMapping(value = "/find/all/response/msg/{pid}",method = RequestMethod.GET)
    public ResponseLeaveWordsToUserGet findResponseToUser(RequestWordsToUserFilterGet req,@PathVariable("pid") int pid){
        UserResponseMsgFilterBo filterBo = new UserResponseMsgFilterBo();
        BeanUtils.copyProperties(req,filterBo);
        filterBo.setPid(pid);
        filterBo.setInvitationId(req.getInvitationId());
        Page<UserMsgEntity> responsePage = userMsgService.findResponseLeaveWords(filterBo);
        ResponseLeaveWordsToUserGet response = new ResponseLeaveWordsToUserGet();
        List<ResponseLeaveWordsToUserGet.Msgs> list = new ArrayList<>();
        responsePage.getData().forEach(resEntity->{
            ResponseLeaveWordsToUserGet.Msgs msgs = new ResponseLeaveWordsToUserGet.Msgs();
            msgs.setAnswerUserId(resEntity.getAnswerUserId());
            UserInfoDto user = userInfoFeignClient.findUserInfo(resEntity.getUserId());
            msgs.setAnswerUserNickname(resEntity.getAnswerUserNickname());
            msgs.setNickname(resEntity.getNickname());
            msgs.setAvatar(user.getAvatar());
            msgs.setMsg(resEntity.getMsg());
            msgs.setCreateTime(DateUtils.getLocalDateTimeStr(resEntity.getCreateTime()));
            msgs.setGender(user.getGender());
            msgs.setUserId(resEntity.getUserId());
            list.add(msgs);
        });
        response.setData(list);
        response.setTotal(responsePage.getTotal());
        return response;
    }

    @ApiOperation(value = "根据帖子id查找所有留言")
    @RequestMapping(value = "/find/all/usermsg/{invitationid}",method = RequestMethod.GET)
    public ResponseAllLeaveWordsGet findAllMsg(@PathVariable("invitationid") int invitationid){
        List<UserMsgEntity> data = userMsgService.findAllUserMsgs(invitationid);
        ResponseAllLeaveWordsGet response = new ResponseAllLeaveWordsGet();
        List<ResponseAllLeaveWordsGet.Msg> list = new ArrayList<>();
        data.forEach(e->{
            ResponseAllLeaveWordsGet.Msg msg = new ResponseAllLeaveWordsGet.Msg();
            BeanUtils.copyProperties(e,msg);
            list.add(msg);
        });
        response.setData(list);
        return response;
    }

    @ApiOperation(value = "查找用户收到的留言")
    @RequestMapping(value = "/query/new/word",method = RequestMethod.GET )
    public ResponseUserMsgInfoGet getUserMsgInfo(RequestUserMsgInfoGet request){
        UserMsgResponseBo filterBo = new UserMsgResponseBo();
        BeanUtils.copyProperties(request,filterBo);
        Page<UserMsgEntity> page = userMsgService.findNewWord(filterBo);
        List<ResponseUserMsgInfoGet.UserMsgInfo> list = new ArrayList<>();
        page.getData().forEach(e->{
            ResponseUserMsgInfoGet.UserMsgInfo userMsgInfo = new ResponseUserMsgInfoGet.UserMsgInfo();
            BeanUtils.copyProperties(e,userMsgInfo);
            userMsgInfo.setAnswerUserId(e.getAnswerUserId());
            userMsgInfo.setAnswerUserNickName(e.getAnswerUserNickname());
            userMsgInfo.setContent(e.getMsg());
            userMsgInfo.setUserId(e.getUserId());
            UserInfoDto info = userInfoFeignClient.findUserInfo(e.getUserId());
            userMsgInfo.setUserAvatr(info.getAvatar());
            userMsgInfo.setUserNickName(e.getNickname());
            userMsgInfo.setCreateTime(DateUtils.getLocalDateTimeStr(e.getCreateTime()));
            userMsgInfo.setGrade(info.getGender());
            if(e.getPid()==0){
                InvitationDto invitationDto = invatitionFeignClient.findInvitationById(1,e.getInvitationId());
                userMsgInfo.setResponseContent(invitationDto.getContent());
            }else{
                userMsgInfo.setId(e.getPid());
                UserMsgEntity userMsgEntity = userMsgService.getResponseMsgContent(e.getPid());
                userMsgInfo.setResponseContent(userMsgEntity.getMsg());
            }
            list.add(userMsgInfo);
        });
        ResponseUserMsgInfoGet response = new ResponseUserMsgInfoGet();
        response.setData(list);
        response.setTotal(page.getTotal());
        return response;
    }

}
