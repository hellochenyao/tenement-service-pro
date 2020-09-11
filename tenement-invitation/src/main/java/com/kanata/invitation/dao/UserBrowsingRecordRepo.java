package com.kanata.invitation.dao;

import com.kanata.core.entity.UserBrowsingRecordEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface UserBrowsingRecordRepo extends CrudRepository<UserBrowsingRecordEntity, Integer>, JpaSpecificationExecutor<UserBrowsingRecordEntity> {
}
