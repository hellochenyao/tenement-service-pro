package com.kanata.invitation.dao;

import com.kanata.core.dto.page.Page;
import com.kanata.invitation.dao.vo.tenementInvitation.InvitationUserInfoVo;
import com.kanata.invitation.dao.vo.userBrowsing.UserBrowsingFilterVo;

public interface UserBrowsingRecordDao {
    Page<InvitationUserInfoVo> findBrowsingRecords(UserBrowsingFilterVo filterVo);
}
