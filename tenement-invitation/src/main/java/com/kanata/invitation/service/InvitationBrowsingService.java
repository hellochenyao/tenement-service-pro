package com.kanata.invitation.service;

import com.kanata.core.dto.page.Page;
import com.kanata.core.entity.UserMsgEntity;
import com.kanata.invitation.dao.vo.tenementInvitation.InvitationUserInfoVo;
import com.kanata.invitation.service.bo.invitationBrowsing.InvitationBrowsingBo;
import com.kanata.invitation.service.bo.invitationBrowsing.UserBrowsingFilterBo;
import com.kanata.invitation.service.bo.tenementInvitation.InvitationUserInfoBo;

import java.util.List;

public interface InvitationBrowsingService {

    void viewDetail(InvitationBrowsingBo invitationBrowsingBo);

    InvitationUserInfoBo findByInvitation(int id);

    void addBrowsingRecord(int userId, int invitationId);

    Page<InvitationUserInfoVo> findBrowsingInvitation(UserBrowsingFilterBo filterBo);

}
