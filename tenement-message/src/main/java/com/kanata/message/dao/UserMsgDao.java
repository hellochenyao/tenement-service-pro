package com.kanata.message.dao;

import com.kanata.core.dto.page.Page;
import com.kanata.core.entity.UserMsgEntity;
import com.kanata.message.dao.vo.userMsg.UserMsgFilterVo;

/**
 * 用户帖子留言
 */
public interface UserMsgDao {
    Page<UserMsgEntity> findNewWord(UserMsgFilterVo filterVo);
}
