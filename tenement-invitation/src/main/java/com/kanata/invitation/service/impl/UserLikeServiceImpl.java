package com.kanata.invitation.service.impl;

import com.kanata.core.common.enums.UserLikeEnum;
import com.kanata.core.dto.page.Page;
import com.kanata.core.entity.TenementInvitationEntity;
import com.kanata.core.entity.UserLikeEntity;
import com.kanata.core.exception.BusinessException;
import com.kanata.core.util.RedisClient;
import com.kanata.core.util.RedisLock;
import com.kanata.invitation.dao.TenementInvitationRepo;
import com.kanata.invitation.dao.UserLikeDao;
import com.kanata.invitation.dao.UserLikeRepo;
import com.kanata.invitation.dao.vo.userLike.GiveUserLikeFilterVo;
import com.kanata.invitation.dao.vo.userLike.GiveUserLikeVo;
import com.kanata.invitation.service.UserLikeService;
import com.kanata.invitation.service.bo.userLike.GiveUserLikeFilterBo;
import com.kanata.invitation.service.bo.userLike.UserLikeBo;
import com.kanata.invitation.service.bo.userLike.UserLikeFilterBo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static java.lang.System.out;

@Service
public class UserLikeServiceImpl implements UserLikeService {

    @Autowired
    UserLikeRepo userLikeRepo;

    @Autowired
    TenementInvitationRepo tenementInvitationRepo;

    private UserLikeDao userLikeDao;

    @Autowired
    RedisClient redisClient;

    @Override
    public void save(UserLikeBo userLikebo) {
        UserLikeEntity userLikeEntity = userLikeRepo.findById(userLikebo.getId()).orElse(null);
        if(userLikeEntity==null){
            userLikeEntity = new UserLikeEntity();
        }
        BeanUtils.copyProperties(userLikebo,userLikeEntity);
        updateInvitationSupportNums(userLikebo.getLikeInvitationId(),userLikebo.getLikeUserId(),userLikebo.getStatus());
        userLikeRepo.save(userLikeEntity);
    }

    @Override
    public Page<UserLikeEntity> getUserLikeListByInvitationId(UserLikeFilterBo userLikeFilterBo) {
        Sort sort = new Sort(Sort.Direction.DESC,"createTime");
        org.springframework.data.domain.Page<UserLikeEntity> page = userLikeRepo.findUserLikeEntitiesByLikeInvitationId(userLikeFilterBo.getLikeInvitationId(), userLikeFilterBo.getStatus(), PageRequest.of(userLikeFilterBo.getPageNo()-1,userLikeFilterBo.getPageSize(),sort));
        Page<UserLikeEntity> pageData = new Page();
        pageData.setData(page.getContent());
        pageData.setTotal(page.getContent().size());
        return pageData;
    }

    @Override
    public Page<UserLikeEntity> getInvitationLikeListByUserId(UserLikeFilterBo userLikeFilterBo) {
        Sort sort = new Sort(Sort.Direction.DESC,"createTime");
        org.springframework.data.domain.Page<UserLikeEntity> page = userLikeRepo.findUserLikeEntitiesByLikeUserId(userLikeFilterBo.getLikeUserId(), userLikeFilterBo.getStatus(), PageRequest.of(userLikeFilterBo.getPageNo()-1,userLikeFilterBo.getPageSize(),sort));
        Page<UserLikeEntity> pageData = new Page();
        pageData.setData(page.getContent());
        pageData.setTotal(page.getContent().size());
        return pageData;
    }

    @Override
    public UserLikeBo findUserLikeByInvitationIdAndUserId(int invitationId, int userId) {
        UserLikeEntity userLikeEntity = userLikeRepo.findByLikeInvitationIdAndLikeUserId(invitationId,userId);
        if(userLikeEntity==null){
            return null;
        }
        UserLikeBo userLikeBo = new UserLikeBo();
        BeanUtils.copyProperties(userLikeEntity,userLikeBo);
        return userLikeBo;
    }

    @Transactional
    @Override
    public void updateInvitationSupportNums(int invitationId, int userId,int likeStatus) {
        TenementInvitationEntity tenementInvitationEntity = tenementInvitationRepo.findById(invitationId).orElse(null);
        UserLikeBo userLikeBo = findUserLikeByInvitationIdAndUserId(invitationId,userId);
        UserLikeEntity userLikeEntity = new UserLikeEntity();
        String key = String.valueOf(invitationId);
        RedisLock redisLock = new RedisLock(key);
        if(tenementInvitationEntity==null){
            throw new BusinessException("NO_INVITATION","帖子状态异常");
        }
        if(redisClient.get(invitationId+"_"+userId+"_"+"support")==null){
            redisClient.set(invitationId+"_"+userId+"_"+"support",1,60);
        }else{
           int num = Integer.parseInt(redisClient.get(invitationId+"_"+userId+"_"+"support"));
           if(num>2){
               throw new BusinessException("TO_OPTION","操作太频繁，请稍后再试");
           }
           num++;
           redisClient.set(invitationId+"_"+userId+"_"+"support",num,60);
        }
        try{
            redisLock.lock();
            if(userLikeBo!=null){
                if(userLikeBo.getStatus()== UserLikeEnum.UNLIKE.getCode()){
                    if(userLikeBo.getStatus()==likeStatus){
                        tenementInvitationEntity.setSupportNums(tenementInvitationEntity.getSupportNums()+1);
                        userLikeRepo.deleteById(userLikeBo.getId());
                    }else{
                        tenementInvitationEntity.setSupportNums(tenementInvitationEntity.getSupportNums()+2);
                        userLikeBo.setStatus(UserLikeEnum.LIKE.getCode());
                        BeanUtils.copyProperties(userLikeBo,userLikeEntity);
                        userLikeEntity.setUpdateTime(LocalDateTime.now());
                        userLikeRepo.save(userLikeEntity);
                    }
                }else if(userLikeBo.getStatus()==UserLikeEnum.LIKE.getCode()){
                    if(userLikeBo.getStatus()==likeStatus){
                        tenementInvitationEntity.setSupportNums(tenementInvitationEntity.getSupportNums()-1);
                        userLikeRepo.deleteById(userLikeBo.getId());
                    }else{
                        tenementInvitationEntity.setSupportNums(tenementInvitationEntity.getSupportNums()-2);
                        userLikeBo.setStatus(UserLikeEnum.UNLIKE.getCode());
                        BeanUtils.copyProperties(userLikeBo,userLikeEntity);
                        userLikeEntity.setUpdateTime(LocalDateTime.now());
                        userLikeRepo.save(userLikeEntity);
                    }
                }
            }else {
                UserLikeBo userLike = new UserLikeBo();
                if(likeStatus==UserLikeEnum.LIKE.getCode()){
                    out.println(tenementInvitationEntity.getSupportNums());
                    tenementInvitationEntity.setSupportNums(tenementInvitationEntity.getSupportNums()+1);
                    userLike.setStatus(0);
                }else if(likeStatus==UserLikeEnum.UNLIKE.getCode()){
                    tenementInvitationEntity.setSupportNums(tenementInvitationEntity.getSupportNums()-1);
                    userLike.setStatus(1);
                }
                userLike.setLikeUserId(userId);
                userLike.setLikeInvitationId(invitationId);
                BeanUtils.copyProperties(userLike,userLikeEntity);
                userLikeEntity.setCreateTime(LocalDateTime.now());
                userLikeEntity.setUpdateTime(LocalDateTime.now());
                userLikeRepo.save(userLikeEntity);
            }
            tenementInvitationRepo.save(tenementInvitationEntity);
        }finally {
            redisLock.unlock();
        }
    }

    @Override
    public Page<GiveUserLikeVo> getUserGiveLike(GiveUserLikeFilterBo filterBo) {
        GiveUserLikeFilterVo filterVo = new GiveUserLikeFilterVo();
        BeanUtils.copyProperties(filterBo,filterVo);
        return userLikeDao.findGiveLikeUser(filterVo);
    }
}
