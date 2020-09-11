package com.kanata.invitation.dao;

import com.kanata.core.dto.page.Page;
import com.kanata.invitation.dao.vo.userCollection.InvitationUserInfoVo;
import com.kanata.invitation.dao.vo.userCollection.UserCollectionFilterVo;

public interface UserCollectionDao {

    Page<InvitationUserInfoVo> findCollections(UserCollectionFilterVo filterVo);

}
