package com.kanata.invitation.service;

import com.kanata.core.dto.page.Page;
import com.kanata.core.entity.UserCollectionEntity;
import com.kanata.invitation.dao.vo.userCollection.InvitationUserInfoVo;
import com.kanata.invitation.service.bo.userCollection.UserCollectionBo;
import com.kanata.invitation.service.bo.userCollection.UserCollectionFilterBo;

public interface UserCollectionService {

    Boolean saveUserCollection(UserCollectionBo userCollectionBo);

    UserCollectionEntity queryCollectStatus(int userId, int invitationId);

    Page<InvitationUserInfoVo> queryAllCollectionInvitations(UserCollectionFilterBo filterBo);
}
