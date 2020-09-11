package com.kanata.message.service;

import com.kanata.core.dto.page.Page;
import com.kanata.core.entity.UserMsgEntity;
import com.kanata.message.service.bo.userMsg.UserMsgBo;
import com.kanata.message.service.bo.userMsg.UserMsgFilterBo;
import com.kanata.message.service.bo.userMsg.UserMsgResponseBo;
import com.kanata.message.service.bo.userMsg.UserResponseMsgFilterBo;

import java.util.List;

/**
 * @author chenyao
 * date 2020-09-08
 */
public interface UserMsgService {

    UserMsgEntity leaveWord(UserMsgBo userMsgBo);

    Page<UserMsgEntity> findLeaveWord(UserMsgFilterBo userMsgFilterBo);

    Page<UserMsgEntity> findResponseLeaveWords(UserResponseMsgFilterBo userResponseMsgFilterBo);

    Integer findLeaveMsgNumsByInvitationId(int id);

    UserMsgEntity getResponseMsgContent(int id);

    List<UserMsgEntity> findAllUserMsgs(int invitationId);

    Page<UserMsgEntity> findNewWord(UserMsgResponseBo filterBo);

}
