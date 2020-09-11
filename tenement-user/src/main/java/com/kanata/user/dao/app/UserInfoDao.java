package com.kanata.user.dao.app;


import com.kanata.core.dto.page.Page;
import com.kanata.core.entity.UserInfoEntity;
import com.kanata.user.dao.app.vo.ConcernFilterVo;

import java.util.List;

public interface UserInfoDao {

    List<UserInfoEntity> findRecords();

    Page<UserInfoEntity> findConcernUsers(ConcernFilterVo concernFilterVo);

}
