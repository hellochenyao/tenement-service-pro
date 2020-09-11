package com.kanata.user.dao.app;


import com.kanata.core.dto.page.Page;
import com.kanata.core.entity.UserInfoEntity;
import com.kanata.user.dao.app.vo.ConcernFilterVo;

public interface ConcernDao {
    Page<UserInfoEntity> findConcernUsers(ConcernFilterVo concernFilterVo);
}
