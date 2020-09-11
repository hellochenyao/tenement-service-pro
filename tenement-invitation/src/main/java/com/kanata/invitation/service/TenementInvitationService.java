package com.kanata.invitation.service;

import com.kanata.core.dto.page.Page;
import com.kanata.core.entity.TenementInvitationEntity;
import com.kanata.invitation.dao.vo.tenementInvitation.InvitationUserInfoVo;
import com.kanata.invitation.service.bo.tenementInvitation.InvitationPublishLogBo;
import com.kanata.invitation.service.bo.tenementInvitation.TenementInvitationBo;
import com.kanata.invitation.service.bo.tenementInvitation.TenementInvitationFilterBo;
import com.kanata.invitation.service.bo.tenementInvitation.TenementInvitationPutBo;

public interface TenementInvitationService {

    //发布帖子
    TenementInvitationEntity create(TenementInvitationBo tenementInvitationBo);

    //查找帖子
    Page<TenementInvitationEntity> findInvitations(TenementInvitationFilterBo tenementInvitationFilterBo);

    void updateInvitation(TenementInvitationPutBo tenementInvitationPutBo);

    void deleteInvitation(int id);

//    Page<UserMsgEntity> findUserMsgs(int invitationId);

    Page<InvitationUserInfoVo> findPublishLog(InvitationPublishLogBo logBo);

    void refreshInvitation(int invitationId);

    void setInvitationStatus(int invitationId, int status);

    TenementInvitationEntity findInvitationById(int id);
}
