package com.kanata.invitation.dao;

import com.kanata.core.entity.TenementInvitationEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface TenementInvitationRepo extends CrudRepository<TenementInvitationEntity, Integer>, JpaSpecificationExecutor<TenementInvitationEntity> {

//    @Query("SELECT t FROM TenementDetailInvitationEntity t WHERE t.type = :type ")
//    Page<TenementInvitationEntity> findByType(@Param("type") int type);
}
