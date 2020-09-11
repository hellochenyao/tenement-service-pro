package com.kanata.message.dao;

import com.kanata.core.entity.LikeDynamicEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LikeDynamicRepo extends CrudRepository<LikeDynamicEntity,String>, JpaSpecificationExecutor<LikeDynamicEntity> {

    List<LikeDynamicEntity> findByUserIdAndDynamicId(Integer userId, Integer dynamicId);

    void deleteById(String id);

    Page<LikeDynamicEntity> findByDynamicId(Integer dynamicId, Pageable pageable);
}
