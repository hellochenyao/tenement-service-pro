package com.kanata.invitation.service.impl;

import com.kanata.core.dto.page.Page;
import com.kanata.core.entity.TenementInvitationEntity;
import com.kanata.core.entity.UserBrowsingRecordEntity;
import com.kanata.core.entity.UserMsgEntity;
import com.kanata.core.exception.BusinessException;
import com.kanata.core.util.DateUtils;
import com.kanata.core.util.RedisLock;
import com.kanata.invitation.dao.TenementInvitationDao;
import com.kanata.invitation.dao.TenementInvitationRepo;
import com.kanata.invitation.dao.UserBrowsingRecordDao;
import com.kanata.invitation.dao.UserBrowsingRecordRepo;
import com.kanata.invitation.dao.vo.tenementInvitation.InvitationUserInfoVo;
import com.kanata.invitation.dao.vo.userBrowsing.UserBrowsingFilterVo;
import com.kanata.invitation.service.InvitationBrowsingService;
import com.kanata.invitation.service.bo.invitationBrowsing.InvitationBrowsingBo;
import com.kanata.invitation.service.bo.invitationBrowsing.UserBrowsingFilterBo;
import com.kanata.invitation.service.bo.tenementInvitation.InvitationUserInfoBo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class InvitationBrowsingServiceImpl implements InvitationBrowsingService {

    @Autowired
    private UserBrowsingRecordRepo userBrowsingRecordRepo;

    @Autowired
    private TenementInvitationRepo tenementInvitationRepo;

    @Autowired
    private TenementInvitationDao tenementInvitationDao;

    @Autowired
    private UserBrowsingRecordDao userBrowsingRecordDao;

    @Override
    public void viewDetail(InvitationBrowsingBo invitationBrowsingBo) {
        UserBrowsingRecordEntity userBrowsingRecordEntity = new UserBrowsingRecordEntity();
        BeanUtils.copyProperties(invitationBrowsingBo,userBrowsingRecordEntity);
        userBrowsingRecordRepo.save(userBrowsingRecordEntity);
        String invitationId = String.valueOf(invitationBrowsingBo.getInvitationId());
        RedisLock lock = new RedisLock(invitationId);
        lock.lock();
        try {
            TenementInvitationEntity tenementInvitationEntity= tenementInvitationRepo.findById(Integer.parseInt(invitationId)).orElse(null);
            if(tenementInvitationEntity!=null){
                tenementInvitationEntity.setViewTimes(tenementInvitationEntity.getViewTimes()+1);
                tenementInvitationRepo.save(tenementInvitationEntity);
            }
        }finally {
            lock.unlock();
        }

    }

    @Override
    public InvitationUserInfoBo findByInvitation(int id) {
        if(id==0){
            throw new BusinessException("PARAM_ERROR","参数不可为空！");
        }
        InvitationUserInfoVo invitationUserInfoVo = tenementInvitationDao.queryInvitationUserInfo(id);
        InvitationUserInfoBo invitationUserInfoBo = new InvitationUserInfoBo();
        BeanUtils.copyProperties(invitationUserInfoVo,invitationUserInfoBo);
        invitationUserInfoBo.setCreateTime(DateUtils.getLocalDateTimeStr(invitationUserInfoVo.getCreateTime()));
        invitationUserInfoBo.setUpdateTime(DateUtils.getLocalDateTimeStr(invitationUserInfoVo.getUpdateTime()));
        invitationUserInfoBo.setDesiredDate(DateUtils.getLocalDateTimeStr(invitationUserInfoVo.getDesiredDate()));
        return invitationUserInfoBo;
    }

    @Override
    public void addBrowsingRecord(int userId, int invitationId) {
        UserBrowsingRecordEntity userBrowsing = new UserBrowsingRecordEntity();
        userBrowsing.setUserId(userId);
        userBrowsing.setInvitationId(invitationId);
        userBrowsing.setBrowsingTime(LocalDateTime.now());
        userBrowsingRecordRepo.save(userBrowsing);
    }

    @Override
    public Page<InvitationUserInfoVo> findBrowsingInvitation(UserBrowsingFilterBo filterBo) {
        UserBrowsingFilterVo filterVo = new UserBrowsingFilterVo();
        BeanUtils.copyProperties(filterBo,filterVo);
        Page<InvitationUserInfoVo> page =  userBrowsingRecordDao.findBrowsingRecords(filterVo);
        return page;
    }

}
