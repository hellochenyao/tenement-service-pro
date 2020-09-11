package com.kanata.message.service.impl;

import com.kanata.core.entity.CircleEntity;
import com.kanata.core.entity.DynamicEntity;
import com.kanata.core.exception.BusinessException;
import com.kanata.message.dao.CircleRepo;
import com.kanata.message.dao.DynamicRepo;
import com.kanata.message.service.DynamicService;
import com.kanata.message.service.bo.dynamic.DynamicBo;
import com.kanata.message.service.bo.dynamic.DynamicFilterBo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DynamicServiceImpl implements DynamicService {

    @Autowired
    private DynamicRepo dynamicRepo;

    @Autowired
    private CircleRepo circleRepo;

    @Override
    public void createDynamic(DynamicBo dynamicBo) {
        DynamicEntity dynamicEntity = new DynamicEntity();
        BeanUtils.copyProperties(dynamicBo,dynamicEntity);
        Optional<CircleEntity> circleEntityOptional = circleRepo.findById(dynamicBo.getCircleId());
        if(!circleEntityOptional.isPresent()){
            throw new BusinessException("CIRCLE_ERROR","圈子不存在！");
        }
        dynamicEntity.setCircleName(circleEntityOptional.get().getCircleName());
        dynamicRepo.save(dynamicEntity);
    }

    @Override
    public Page<DynamicEntity> query(DynamicFilterBo dynamicFilterBo) {
        Sort sort= new Sort(Sort.Direction.DESC, "createTime");
        Pageable pageable = PageRequest.of(dynamicFilterBo.getPageNo()-1,dynamicFilterBo.getPageSize(),sort);
        if(dynamicFilterBo.getType()==0){
            Specification<DynamicEntity> specification = (root, query, cb)->{
                List<Predicate> predicates = new ArrayList<>();
                if(dynamicFilterBo.getDynamicId()!=0){
                    predicates.add(cb.equal(root.get("id"),dynamicFilterBo.getDynamicId()));
                }
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            };
            return dynamicRepo.findAll(specification,pageable);
        }else{
            return dynamicRepo.findJoinConcern(dynamicFilterBo.getUserId(),pageable);
        }

    }
}
