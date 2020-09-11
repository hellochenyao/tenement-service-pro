package com.kanata.invitation.service.impl;

import com.kanata.core.entity.TipOffsRecordEntity;
import com.kanata.invitation.dao.TipOffsRecordRepo;
import com.kanata.invitation.service.TipOffsService;
import com.kanata.invitation.service.bo.tipoffs.UserTipOffsBo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Created by mumu on 2019/3/27.
 */
@Service
public class TipOffsServiceImpl implements TipOffsService {

    @Autowired
    private TipOffsRecordRepo tipOffsRecordRepo;

    /**
     * 举报 用户/帖子
     *
     * @param userTipOffsBo
     */
    @Override
    public void tipOffs(UserTipOffsBo userTipOffsBo) {

        TipOffsRecordEntity tipOffsRecordEntity = new TipOffsRecordEntity();

        BeanUtils.copyProperties(userTipOffsBo,tipOffsRecordEntity);
        tipOffsRecordEntity.setUserId(userTipOffsBo.getUserId());
        tipOffsRecordRepo.save(tipOffsRecordEntity);

    }

}
