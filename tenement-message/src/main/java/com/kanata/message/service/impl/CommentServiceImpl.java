package com.kanata.message.service.impl;

import com.kanata.core.entity.CommentEntity;
import com.kanata.core.exception.BusinessException;
import com.kanata.feign.api.user.IUserInfoFeignClient;
import com.kanata.feign.dto.UserInfoDto;
import com.kanata.message.dao.CommentRepo;
import com.kanata.message.service.CommentService;
import com.kanata.message.service.bo.comment.CommentBo;
import com.kanata.message.service.bo.comment.CommentFilterBo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepo commentRepo;

    @Autowired
    private IUserInfoFeignClient userInfoFeignClient;

    @Override
    public CommentEntity createComment(CommentBo commentBo) {
        CommentEntity commentEntity = new CommentEntity();
        BeanUtils.copyProperties(commentBo,commentEntity);
        if(commentBo.getMessagedId()!=0){
            UserInfoDto userInfoDto = userInfoFeignClient.findUserInfo(commentBo.getCommentedId());
            if(userInfoDto.getId() == -1){
                throw new BusinessException("USER_NOT_EXIST", "用户不存在");
            }
           String content = commentEntity.setNewContent(commentBo.getContent(),userInfoDto.getNickName());
           commentEntity.setContent(content);
        }
       return commentRepo.save(commentEntity);
    }

    @Override
    public Page<CommentEntity> queryComment(CommentFilterBo commentFilterBo, Pageable pageable) {
        return commentRepo.findByDynamicId(commentFilterBo.getDynamicId(),pageable);
    }
}
