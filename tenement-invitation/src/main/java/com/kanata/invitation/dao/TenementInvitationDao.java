package com.kanata.invitation.dao;

import com.kanata.core.dto.page.Page;
import com.kanata.core.entity.TenementInvitationEntity;
import com.kanata.invitation.dao.vo.tenementInvitation.InvitationLogFilterVo;
import com.kanata.invitation.dao.vo.tenementInvitation.InvitationUserInfoVo;
import com.kanata.invitation.dao.vo.tenementInvitation.TenementInvitationFilterVo;

public interface TenementInvitationDao {

    Page<TenementInvitationEntity> findInvitation(TenementInvitationFilterVo tenementInvitationFilterVo);

    InvitationUserInfoVo queryInvitationUserInfo(int id);

    Page<InvitationUserInfoVo> findPublishLog(InvitationLogFilterVo filterVo);

}
