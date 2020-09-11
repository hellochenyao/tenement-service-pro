package com.kanata.message.service.impl;

import com.kanata.core.dto.page.Page;
import com.kanata.core.entity.TenementInvitationEntity;
import com.kanata.core.entity.UserInfoEntity;
import com.kanata.core.entity.UserMsgEntity;
import com.kanata.core.exception.BusinessException;
import com.kanata.feign.api.invitation.IInvatitionFeignClient;
import com.kanata.feign.api.user.IUserInfoFeignClient;
import com.kanata.feign.dto.InvitationDto;
import com.kanata.feign.dto.UserInfoDto;
import com.kanata.message.dao.UserMsgDao;
import com.kanata.message.dao.UserMsgRepo;
import com.kanata.message.dao.vo.userMsg.UserMsgFilterVo;
import com.kanata.message.service.UserMsgService;
import com.kanata.message.service.bo.userMsg.UserMsgBo;
import com.kanata.message.service.bo.userMsg.UserMsgFilterBo;
import com.kanata.message.service.bo.userMsg.UserMsgResponseBo;
import com.kanata.message.service.bo.userMsg.UserResponseMsgFilterBo;
import org.apache.catalina.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author chenyao
 * date 2020-09-08
 */
@Service
public class UserMsgServiceImpl implements UserMsgService {

    @Autowired
    private UserMsgDao userMsgDao;

    @Autowired
    private IInvatitionFeignClient invatitionFeignClient;

    @Autowired
    private IUserInfoFeignClient userInfoFeignClient;

    @Autowired
    private UserMsgRepo userMsgRepo;



    @Override
    public UserMsgEntity leaveWord(UserMsgBo userMsgBo) {
        InvitationDto invitationDto = invatitionFeignClient.findInvitationById(1,userMsgBo.getInvitationId());
        if(invitationDto.getId() == -1){
            throw new BusinessException("NO_INVITATION","该帖子不存在");
        }
        UserInfoDto answerUserInfo = userInfoFeignClient.findUserInfo(userMsgBo.getResponseUserId());
        UserInfoDto userInfo = userInfoFeignClient.findUserInfo(userMsgBo.getUserId());
        UserMsgEntity userMsgEntity = new UserMsgEntity();
        userMsgEntity.setAnswerUserId(answerUserInfo.getId());
        userMsgEntity.setCreateTime(LocalDateTime.now());
        userMsgEntity.setAnswerUserNickname(answerUserInfo.getNickName());
        userMsgEntity.setNickname(userInfo.getNickName());
        userMsgEntity.setPid(userMsgBo.getAnswerMsgId());
        BeanUtils.copyProperties(userMsgBo,userMsgEntity);
        return userMsgRepo.save(userMsgEntity);
    }

    @Override
    public Page<UserMsgEntity> findLeaveWord(UserMsgFilterBo userMsgFilterBo) {
        Sort sort = new Sort(Sort.Direction.ASC, "createTime");
        org.springframework.data.domain.Page<UserMsgEntity> pageData = userMsgRepo.findByInvitationId(userMsgFilterBo.getInvitationId(), PageRequest.of(userMsgFilterBo.getPageNo()-1,userMsgFilterBo.getPageSize(),sort));
        int total = userMsgRepo.findMsgCountByInvitationId(userMsgFilterBo.getInvitationId());
        Page<UserMsgEntity> page = new Page<>();
        page.setTotal(total);
        page.setData(pageData.getContent());
        return page;
    }

    @Override
    public Page<UserMsgEntity> findResponseLeaveWords(UserResponseMsgFilterBo userResponseMsgFilterBo) {
        Sort sort = new Sort(Sort.Direction.ASC, "createTime");
        org.springframework.data.domain.Page<UserMsgEntity> pageData = userMsgRepo.findByPid(userResponseMsgFilterBo.getPid(),userResponseMsgFilterBo.getInvitationId(), PageRequest.of(userResponseMsgFilterBo.getPageNo()-1,userResponseMsgFilterBo.getPageSize(),sort));
        int total = userMsgRepo.findMsgCountByPid(userResponseMsgFilterBo.getPid(),userResponseMsgFilterBo.getInvitationId());
        Page<UserMsgEntity> page = new Page<>();
        page.setTotal(total);
        page.setData(pageData.getContent());
        return page;
    }

    @Override
    public Integer findLeaveMsgNumsByInvitationId(int id) {
        List<UserMsgEntity> list = userMsgRepo.findByInvitationId(id);
        return list.size();
    }


    @Override
    public UserMsgEntity getResponseMsgContent(int id) {
        return userMsgRepo.findById(id).orElse(null);
    }

    @Override
    public List<UserMsgEntity> findAllUserMsgs(int invitationId) {
        return userMsgRepo.findByInvitationId(invitationId);
    }

    @Override
    public Page<UserMsgEntity> findNewWord(UserMsgResponseBo filterBo) {
        UserMsgFilterVo filterVo = new UserMsgFilterVo();
        BeanUtils.copyProperties(filterBo,filterVo);
        return userMsgDao.findNewWord(filterVo);
    }
}
