package com.kanata.message.service;


import com.kanata.core.dto.page.Page;
import com.kanata.core.entity.PrivateMsgEntity;
import com.kanata.message.service.bo.privateMsg.PrivateMsgBo;
import com.kanata.message.service.bo.privateMsg.PrivateMsgFilterBo;
import com.kanata.message.service.bo.privateMsg.PrivateMsgReceiveUserFilterBo;

public interface PrivateMsgService {

    void saveMsg(PrivateMsgBo privateMsgBo);

    Page<PrivateMsgEntity> findHistoryMsg(PrivateMsgFilterBo privateMsgFilterBo);

    Page<PrivateMsgEntity> findUserReceiveMsg(PrivateMsgReceiveUserFilterBo privateMsgFilterBo);

    Integer findNoReadNums(int userid, int receiveUserid, int readType);

    void deleteAllMsg(int userid, int receiveUserid);
}
