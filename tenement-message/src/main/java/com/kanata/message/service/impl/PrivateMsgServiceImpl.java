package com.kanata.message.service.impl;

import com.kanata.core.dto.page.Page;
import com.kanata.core.entity.PrivateMsgEntity;
import com.kanata.core.exception.BusinessException;
import com.kanata.feign.api.user.IUserRelationFeignClient;
import com.kanata.feign.dto.UserRelationDto;
import com.kanata.message.dao.PrivateMsgDao;
import com.kanata.message.dao.PrivateMsgRepo;
import com.kanata.message.dao.vo.privateMsg.PrivateMsgUserReceiveFilterVo;
import com.kanata.message.service.PrivateMsgService;
import com.kanata.message.service.bo.privateMsg.PrivateMsgBo;
import com.kanata.message.service.bo.privateMsg.PrivateMsgFilterBo;
import com.kanata.message.service.bo.privateMsg.PrivateMsgReceiveUserFilterBo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class PrivateMsgServiceImpl implements PrivateMsgService {

    @Autowired
    private PrivateMsgRepo privateMsgRepo;

    @Autowired
    private IUserRelationFeignClient userRelationFeignClient;

    @Autowired
    private PrivateMsgDao privateMsgDao;

    @Override
    public void saveMsg(PrivateMsgBo privateMsgBo) {
        UserRelationDto userRelation = userRelationFeignClient.findRelation(privateMsgBo.getUserid(),privateMsgBo.getReceiveUserid());
        if(userRelation!=null && userRelation.getType()==-1){
            throw new BusinessException("RELATION_ERROR","发送方已被接收方拉黑！");
        }
        PrivateMsgEntity privateMsgEntity = new PrivateMsgEntity();
        BeanUtils.copyProperties(privateMsgBo,privateMsgEntity);
        privateMsgRepo.save(privateMsgEntity);
    }

    @Override
    public Page<PrivateMsgEntity> findHistoryMsg(PrivateMsgFilterBo privateMsgFilterBo) {
        Sort sort = new Sort(Sort.Direction.DESC,"createTime");
        PageRequest pageRequest = PageRequest.of(privateMsgFilterBo.getPageNo()-1,privateMsgFilterBo.getPageSize(),sort);
        org.springframework.data.domain.Page<PrivateMsgEntity> page = privateMsgRepo.findHistoryMsg(privateMsgFilterBo.getUserid(),privateMsgFilterBo.getReceiveUserid(),pageRequest);
        Integer total = privateMsgRepo.findHistoryNums(privateMsgFilterBo.getUserid(),privateMsgFilterBo.getReceiveUserid());
        Page pageData = new Page();
        pageData.setData(page.getContent());
        pageData.setTotal(total);
        return pageData;
    }

    @Override
    public Page<PrivateMsgEntity> findUserReceiveMsg(PrivateMsgReceiveUserFilterBo privateMsgFilterBo) {
        PrivateMsgUserReceiveFilterVo privateVo = new PrivateMsgUserReceiveFilterVo();
        BeanUtils.copyProperties(privateMsgFilterBo,privateVo);
        Page<PrivateMsgEntity> page = privateMsgDao.findConnectMsg(privateVo);
        return page;
    }

    @Override
    public Integer findNoReadNums(int userid, int receiveUserid, int readType) {

        return privateMsgRepo.findNoReadNums(userid,receiveUserid,readType);
    }

    @Override
    public void deleteAllMsg(int userid, int receiveUserid) {
        privateMsgDao.deleteMsg(userid,receiveUserid);
    }
}
