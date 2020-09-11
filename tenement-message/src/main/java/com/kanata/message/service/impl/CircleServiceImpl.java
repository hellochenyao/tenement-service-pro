package com.kanata.message.service.impl;

import com.kanata.core.entity.CircleEntity;
import com.kanata.core.exception.BusinessException;
import com.kanata.message.dao.CircleRepo;
import com.kanata.message.service.CircleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CircleServiceImpl implements CircleService {

    @Autowired
    private CircleRepo circleRepo;

    @Override
    public void createCircle(CircleEntity circleEntity) {
        CircleEntity circle = circleRepo.findByCircleName(circleEntity.getCircleName());
        if(circle!=null){
            throw new BusinessException("EXIST","该圈子已存在！");
        }
        circleRepo.save(circleEntity);
    }

    @Override
    public Page<CircleEntity> findCircle(int id, Pageable pageable) {
        return circleRepo.findCicle(id,pageable);
    }
}
