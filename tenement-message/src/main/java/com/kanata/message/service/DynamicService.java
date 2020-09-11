package com.kanata.message.service;

import com.kanata.core.entity.DynamicEntity;
import com.kanata.message.service.bo.dynamic.DynamicBo;
import com.kanata.message.service.bo.dynamic.DynamicFilterBo;
import org.springframework.data.domain.Page;

public interface DynamicService {

    void createDynamic(DynamicBo dynamicBo);

    Page<DynamicEntity> query(DynamicFilterBo dynamicFilterBo);
}
