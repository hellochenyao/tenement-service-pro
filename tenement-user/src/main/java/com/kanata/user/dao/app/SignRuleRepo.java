package com.kanata.user.dao.app;

import com.kanata.core.entity.SignRuleEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @author chenyao
 * date 2020-09-24
 */
public interface SignRuleRepo extends JpaSpecificationExecutor<SignRuleEntity>, PagingAndSortingRepository<SignRuleEntity,Integer> {
}
