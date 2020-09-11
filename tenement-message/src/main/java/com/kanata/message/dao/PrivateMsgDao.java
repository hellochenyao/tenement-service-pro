package com.kanata.message.dao;


import com.kanata.core.dto.page.Page;
import com.kanata.core.entity.PrivateMsgEntity;
import com.kanata.message.dao.vo.privateMsg.PrivateMsgUserReceiveFilterVo;

public interface PrivateMsgDao {
    Page<PrivateMsgEntity> findConnectMsg(PrivateMsgUserReceiveFilterVo privateFilter);

    void deleteMsg(int userid, int receiveUserid);
}
