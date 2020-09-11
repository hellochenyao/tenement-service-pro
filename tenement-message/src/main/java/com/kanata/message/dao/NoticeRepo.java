package com.kanata.message.dao;

import com.kanata.core.entity.NoticeEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface NoticeRepo extends CrudRepository<NoticeEntity,Integer>, JpaSpecificationExecutor<NoticeEntity> {

}
