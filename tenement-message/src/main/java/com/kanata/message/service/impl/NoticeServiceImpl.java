package com.kanata.message.service.impl;

import com.kanata.core.entity.NoticeEntity;
import com.kanata.message.dao.NoticeRepo;
import com.kanata.message.service.NoticeService;
import com.kanata.message.service.bo.notice.NoticeBo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class NoticeServiceImpl implements NoticeService {

    @Autowired
    private NoticeRepo noticeRepo;

    @Override
    public void createNotice(NoticeBo noticeBo) {
        NoticeEntity noticeEntity = new NoticeEntity();
        BeanUtils.copyProperties(noticeBo,noticeEntity);
        noticeEntity.setCreateTime(LocalDateTime.now());
        noticeEntity.setUpdateTime(LocalDateTime.now());
        noticeRepo.save(noticeEntity);
    }
}
