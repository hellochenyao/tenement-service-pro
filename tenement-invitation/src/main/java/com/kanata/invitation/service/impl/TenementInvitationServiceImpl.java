package com.kanata.invitation.service.impl;

import com.kanata.core.common.enums.ConcernType;
import com.kanata.core.common.websocket.WebSocketServer;
import com.kanata.core.dto.page.Page;
import com.kanata.core.entity.ConcernEntity;
import com.kanata.core.entity.TenementInvitationEntity;
import com.kanata.core.exception.BusinessException;
import com.kanata.feign.api.user.IUserInfoFeignClient;
import com.kanata.feign.dto.FansDto;
import com.kanata.invitation.dao.TenementInvitationDao;
import com.kanata.invitation.dao.TenementInvitationRepo;
import com.kanata.invitation.dao.vo.tenementInvitation.InvitationLogFilterVo;
import com.kanata.invitation.dao.vo.tenementInvitation.InvitationUserInfoVo;
import com.kanata.invitation.dao.vo.tenementInvitation.TenementInvitationFilterVo;
import com.kanata.invitation.service.TenementInvitationService;
import com.kanata.invitation.service.bo.tenementInvitation.InvitationPublishLogBo;
import com.kanata.invitation.service.bo.tenementInvitation.TenementInvitationBo;
import com.kanata.invitation.service.bo.tenementInvitation.TenementInvitationFilterBo;
import com.kanata.invitation.service.bo.tenementInvitation.TenementInvitationPutBo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TenementInvitationServiceImpl implements TenementInvitationService {

    @Autowired
    private TenementInvitationRepo tenementInvitationRepo;

    @Autowired
    private TenementInvitationDao tenementInvitationDao;

    @Value("${qqmap.key}")
    private String amapKey;

    @Autowired
    private IUserInfoFeignClient userInfoFeignClient;


    @Override
    public Page<TenementInvitationEntity> findInvitations(TenementInvitationFilterBo tenementInvitationFilterBo) {
        TenementInvitationFilterVo tenementInvitationFilterVo = new TenementInvitationFilterVo();
        BeanUtils.copyProperties(tenementInvitationFilterBo, tenementInvitationFilterVo);
        Page<TenementInvitationEntity> pageData = tenementInvitationDao.findInvitation(tenementInvitationFilterVo);
        return pageData;
    }

    @Override
    public TenementInvitationEntity create(TenementInvitationBo tenementInvitationBo) {
        TenementInvitationEntity tenementInvitationEntity = new TenementInvitationEntity();
        BeanUtils.copyProperties(tenementInvitationBo, tenementInvitationEntity);
        tenementInvitationEntity.setStatus(1);
//        String httpAddr = "https://restapi.amap.com/v3/geocode/regeo?key={key}&location={location}";
//        Map<String,String> param = new HashMap<>();
//        param.put("location",tenementInvitationBo.getLocation());
//        param.put("key",amapKey);
//        String response = restTemplate.getForObject(httpAddr,String.class,param);
//        Object resObj = JSONObject.parseObject(response);
//        String city = ((JSONObject) resObj).getJSONObject("regeocode").getJSONObject("addressComponent").getString("city");
        TenementInvitationEntity responseInvitation = tenementInvitationRepo.save(tenementInvitationEntity);
        FansDto fansDto = userInfoFeignClient.findFans(1,tenementInvitationBo.getUserId(),ConcernType.USER);
        fansDto.getConcernInfoList().forEach(e->{
            WebSocketServer.sendInfo(responseInvitation.getId(),"concernInvitation",e.getUserid(),-1);
        });
        return responseInvitation;
    }

    @Override
    public void updateInvitation(TenementInvitationPutBo tenementInvitationPutBo) {
        TenementInvitationEntity tenementInvitationEntity = tenementInvitationRepo.findById(tenementInvitationPutBo.getId()).get();
        BeanUtils.copyProperties(tenementInvitationPutBo, tenementInvitationEntity);
        tenementInvitationRepo.save(tenementInvitationEntity);
    }

    @Override
    public void deleteInvitation(int id) {
        TenementInvitationEntity tenementInvitationEntity = tenementInvitationRepo.findById(id).orElse(null);
        if (tenementInvitationEntity != null) {
            tenementInvitationRepo.delete(tenementInvitationEntity);
        }
    }

//    @Override
//    public Page<UserMsgEntity> findUserMsgs(int invitationId) {
//        return null;
//    }

    @Override
    public Page<InvitationUserInfoVo> findPublishLog(InvitationPublishLogBo logBo) {
        InvitationLogFilterVo filterVo = new InvitationLogFilterVo();
        BeanUtils.copyProperties(logBo, filterVo);
        return tenementInvitationDao.findPublishLog(filterVo);
    }

    /**
     * 顶贴
     */
    @Override
    public void refreshInvitation(int invitationId) {
        TenementInvitationEntity tenementInvitationEntity = tenementInvitationRepo.findById(invitationId).orElse(null);
        if (tenementInvitationEntity != null) {
            tenementInvitationEntity.setUpdateTime(LocalDateTime.now());
        } else {
            throw new BusinessException("INVITATION_NOT_FOUND", "帖子不存在");
        }
        tenementInvitationRepo.save(tenementInvitationEntity);
    }

    /**
     * 修改帖子状态
     *
     * @param invitationId
     * @param status
     */
    @Override
    public void setInvitationStatus(int invitationId, int status) {
        TenementInvitationEntity tenementInvitationEntity = tenementInvitationRepo.findById(invitationId).orElse(null);
        if (tenementInvitationEntity != null) {
            tenementInvitationEntity.setStatus(status);
        } else {
            throw new BusinessException("INVITATION_NOT_FOUND", "帖子不存在");
        }
        tenementInvitationRepo.save(tenementInvitationEntity);
    }

    /**
     * 查找帖子
     *
     * @param id
     * @return
     */
    @Override
    public TenementInvitationEntity findInvitationById(int id) {
        TenementInvitationEntity tenementInvitationEntity = tenementInvitationRepo.findById(id).orElse(null);
        return tenementInvitationEntity;
    }
}
