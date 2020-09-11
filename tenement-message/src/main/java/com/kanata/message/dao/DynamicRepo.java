package com.kanata.message.dao;

import com.kanata.core.entity.DynamicEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface DynamicRepo extends CrudRepository<DynamicEntity,Integer>, JpaSpecificationExecutor<DynamicEntity> {
    @Query("select t from DynamicEntity t inner join ConcernEntity c on t.userId=c.toUserid where t.userId = ?1")
    Page<DynamicEntity> findJoinConcern(Integer userId, Pageable pageable);
}
