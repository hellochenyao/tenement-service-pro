package com.kanata.message.dao;

import com.kanata.core.entity.CommentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CommentRepo extends CrudRepository<CommentEntity,Integer>, JpaSpecificationExecutor<CommentEntity> {
    Page<CommentEntity> findByDynamicId(Integer dynamicId, Pageable pageable);

    List<CommentEntity> findByDynamicId(Integer dynamicId);
}
