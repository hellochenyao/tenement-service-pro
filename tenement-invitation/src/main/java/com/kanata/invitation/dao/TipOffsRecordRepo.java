package com.kanata.invitation.dao;

import com.kanata.core.entity.TipOffsRecordEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by mumu on 2019/4/18.
 */
public interface TipOffsRecordRepo extends CrudRepository<TipOffsRecordEntity, Integer>, JpaSpecificationExecutor<TipOffsRecordEntity> {


}
