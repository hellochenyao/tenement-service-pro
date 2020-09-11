package com.kanata.invitation.dao;

import com.kanata.core.entity.UserCollectionEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface UserCollectionRepo extends CrudRepository<UserCollectionEntity, Integer>, JpaSpecificationExecutor<UserCollectionEntity> {

    UserCollectionEntity findByUserIdAndInvitationId(int userId, int invitationId);

}
