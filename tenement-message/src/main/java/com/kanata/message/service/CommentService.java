package com.kanata.message.service;

import com.kanata.core.entity.CommentEntity;
import com.kanata.message.service.bo.comment.CommentBo;
import com.kanata.message.service.bo.comment.CommentFilterBo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentService {

    CommentEntity createComment(CommentBo commentBo);

    Page<CommentEntity> queryComment(CommentFilterBo commentFilterBo, Pageable pageable);
}
