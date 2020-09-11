package com.kanata.message.controller;

import com.kanata.core.entity.CommentEntity;
import com.kanata.core.entity.DynamicEntity;
import com.kanata.core.exception.BusinessException;
import com.kanata.feign.api.user.IUserInfoFeignClient;
import com.kanata.feign.dto.UserInfoDto;
import com.kanata.message.controller.api.comment.RequestCommentFilterGet;
import com.kanata.message.controller.api.comment.RequestCreateCommentPost;
import com.kanata.message.controller.api.comment.ResponseCommentGet;
import com.kanata.message.controller.api.comment.ResponseCommentPost;
import com.kanata.message.dao.CommentRepo;
import com.kanata.message.dao.DynamicRepo;
import com.kanata.message.service.CommentService;

import com.kanata.message.service.bo.comment.CommentBo;
import com.kanata.message.service.bo.comment.CommentFilterBo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@Api(tags = "APP评论模块")
@RequestMapping(value = "/app/user/comment/{userId}")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private DynamicRepo dynamicRepo;

    @Autowired
    private CommentRepo commentRepo;

    @Autowired
    private IUserInfoFeignClient userInfoFeignClient;

    @ApiOperation("用户新建评论")
    @PostMapping(value = "/create/comment")
    public ResponseCommentPost createComment(@RequestBody RequestCreateCommentPost request, @PathVariable int userId){
        CommentBo commentBo = new CommentBo();
        commentBo.setUserId(userId);
        BeanUtils.copyProperties(request,commentBo);
        if(request.getMessagedId()==0){
            Optional<DynamicEntity> optional = dynamicRepo.findById(request.getDynamicId());
            if(!optional.isPresent()){
                throw new BusinessException("DYNAMIC_NOT_EXIST","动态不存在！");
            }
            commentBo.setCommentedId(optional.get().getUserId());
        }else{
            Optional<CommentEntity> optional = commentRepo.findById(request.getMessagedId());
            if(!optional.isPresent()){
                throw new BusinessException("COMMENT_NOT_EXIST","评论不存在！");
            }
            commentBo.setCommentedId(optional.get().getUserId());
        }
        CommentEntity commentEntity = commentService.createComment(commentBo);
        ResponseCommentPost responseCommentPost = new ResponseCommentPost();
        BeanUtils.copyProperties(commentEntity,responseCommentPost);
        return responseCommentPost;
    }

    @ApiOperation("用户查看评论")
    @GetMapping(value = "/query/comment")
    public ResponseCommentGet queryComment(RequestCommentFilterGet requestCommentFilterGet){
        CommentFilterBo commentFilterBo = new CommentFilterBo();
        commentFilterBo.setDynamicId(requestCommentFilterGet.getDynamicId());
        Sort sort = new Sort(Sort.Direction.ASC,"createTime");
        Pageable pageable = PageRequest.of(requestCommentFilterGet.getPageNo()-1,requestCommentFilterGet.getPageSize(),sort);
        org.springframework.data.domain.Page<CommentEntity> pageData = commentService.queryComment(commentFilterBo,pageable);
        ResponseCommentGet response = new ResponseCommentGet();
        List<ResponseCommentGet.Comment> commentList = pageData.stream().map(e->{
            ResponseCommentGet.Comment data = new ResponseCommentGet.Comment();
            UserInfoDto userInfoDto = userInfoFeignClient.findUserInfo(e.getUserId());
            BeanUtils.copyProperties(e,data);
            data.setAvatar(userInfoDto.getAvatar());
            data.setUserName(userInfoDto.getNickName());
            return data;
        }).collect(Collectors.toList());
        response.setComments(commentList);
        response.setTotal((int)pageData.getTotalElements());
        return response;
    }

}
