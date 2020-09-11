package com.kanata.invitation.service.impl;

import com.kanata.core.dto.page.Page;
import com.kanata.core.entity.UserCollectionEntity;
import com.kanata.invitation.dao.UserCollectionDao;
import com.kanata.invitation.dao.UserCollectionRepo;
import com.kanata.invitation.dao.vo.userCollection.InvitationUserInfoVo;
import com.kanata.invitation.dao.vo.userCollection.UserCollectionFilterVo;
import com.kanata.invitation.service.UserCollectionService;
import com.kanata.invitation.service.bo.userCollection.UserCollectionBo;
import com.kanata.invitation.service.bo.userCollection.UserCollectionFilterBo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserCollectionServiceImpl implements UserCollectionService {

    @Autowired
    private UserCollectionRepo userCollectionRepo;

    @Autowired
    private UserCollectionDao userCollectionDao;

    @Override
    public Boolean saveUserCollection(UserCollectionBo userCollectionBo) {
        UserCollectionEntity userCollect = userCollectionRepo.findByUserIdAndInvitationId(userCollectionBo.getUserId(),userCollectionBo.getInvitationId());
        if(userCollect==null){
            userCollect = new UserCollectionEntity();
            BeanUtils.copyProperties(userCollectionBo,userCollect);
            userCollect.setCollectTime(LocalDateTime.now());
            userCollectionRepo.save(userCollect);
            return true;
        }else{
            userCollectionRepo.delete(userCollect);
            return false;
        }
    }

    @Override
    public UserCollectionEntity queryCollectStatus(int userId, int invitationId) {
        return userCollectionRepo.findByUserIdAndInvitationId(userId,invitationId);
    }

    @Override
    public Page<InvitationUserInfoVo> queryAllCollectionInvitations(UserCollectionFilterBo filterBo) {
        UserCollectionFilterVo filterVo = new UserCollectionFilterVo();
        BeanUtils.copyProperties(filterBo,filterVo);
        return userCollectionDao.findCollections(filterVo);
    }
}
