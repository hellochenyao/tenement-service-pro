package com.kanata.user.service.app.impl;

import com.kanata.core.common.enums.ConcernType;
import com.kanata.core.dto.page.Page;
import com.kanata.core.entity.ConcernEntity;
import com.kanata.core.entity.UserInfoEntity;
import com.kanata.core.exception.BusinessException;
import com.kanata.user.dao.app.ConcernDao;
import com.kanata.user.dao.app.ConcernRepo;
import com.kanata.user.dao.app.vo.ConcernFilterVo;
import com.kanata.user.dao.app.vo.UserInfoVo;
import com.kanata.user.service.app.ConcernService;
import com.kanata.user.service.app.UserInfoService;
import com.kanata.user.service.app.bo.concern.ConcernFilterBo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ConcernServiceImpl implements ConcernService {

    @Autowired
    private ConcernRepo concernRepo;

    @Autowired
    private UserInfoService userInfoService;

//    @Autowired
//    private NoticeService noticeService;

    @Autowired
    private ConcernDao concernDao;


    @Override
    public void saveUserConcern(int userid, int toUserid,ConcernType concernType) {
        ConcernEntity concernEntity = new ConcernEntity(userid,toUserid,concernType,LocalDateTime.now(),LocalDateTime.now());
        try{
            concernRepo.saveConcern(concernEntity.getId(),
                    concernEntity.getUserid(),
                    concernEntity.getToUserid(),
                    concernEntity.getUpdateTime(),
                    concernEntity.getConcernType().toString(),
                    concernEntity.getCreateTime()
            );
        }catch (Exception e){
            throw new BusinessException("HAVE_CONCERN","您已关注过改用户，请勿重复关注！");
        }
//        NoticeBo noticeBo = new NoticeBo();
        UserInfoVo userInfo = userInfoService.info(userid);
//        noticeBo.setContent(userInfo.getNickName()+"关注了你");
//        noticeBo.setToUserid(toUserid);
//        noticeService.createNotice(noticeBo);
//        WebSocketServer.sendInfo(null,"adminNotice",toUserid,-1);
    }

    @Override
    public void deletedUserConcern(int userid, int toUserid,ConcernType concernType) {
        ConcernEntity concernEntity = concernRepo.findByUseridAndToUseridAndConcernType(userid,toUserid,concernType);
        if(concernEntity!=null){
            concernRepo.delete(concernEntity);
            UserInfoVo userInfo = userInfoService.info(userid);
//            NoticeBo noticeBo = new NoticeBo();
//            noticeBo.setToUserid(toUserid);
//            noticeBo.setContent(userInfo.getNickName()+"取关了你");
////            noticeService.createNotice(noticeBo);
//            WebSocketServer.sendInfo(null,"adminNotice",toUserid,-1);
        }else{
            throw new BusinessException("CANCEL_ERROR","取关失败，你还未关注过改用户!");
        }
    }

    @Override
    public Integer queryConcernNums(int userid,ConcernType concernType) {
        return concernRepo.findConcernNums(userid,concernType);
    }

    @Override
    public Integer queryAdmiresNums(int toUserid,ConcernType concernType) {
        return concernRepo.findAdmiresNums(toUserid,concernType);
    }

    @Override
    public Page<UserInfoEntity> findConcernList(ConcernFilterBo concernFilterBo) {
        ConcernFilterVo concernFilterVo = new ConcernFilterVo();
        BeanUtils.copyProperties(concernFilterBo,concernFilterVo);
        Page<UserInfoEntity> page = concernDao.findConcernUsers(concernFilterVo);
        return page;
    }

    @Override
    public Integer findIsConcern(int userid, int toUserid, ConcernType concernType) {
        ConcernEntity concernEntity = concernRepo.findByUseridAndToUseridAndConcernType(userid,toUserid,concernType);
        ConcernEntity fanEntity = concernRepo.findByUseridAndToUseridAndConcernType(toUserid,userid,concernType);
        if(concernEntity!=null&&fanEntity!=null){
            return 2;
        }
        if(concernEntity!=null){
            return 1;
        }
        return 0;
    }

    @Override
    public List<ConcernEntity> findConcerns(int toUserid,ConcernType concernType) {
        return concernRepo.findByToUseridAndConcernType(toUserid,concernType);
    }
}
