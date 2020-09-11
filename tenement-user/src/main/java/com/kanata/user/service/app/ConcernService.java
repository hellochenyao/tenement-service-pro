package com.kanata.user.service.app;

import com.kanata.core.common.enums.ConcernType;
import com.kanata.core.dto.page.Page;
import com.kanata.core.entity.ConcernEntity;
import com.kanata.core.entity.UserInfoEntity;
import com.kanata.user.service.app.bo.concern.ConcernFilterBo;

import java.util.List;

public interface ConcernService {

    /**
     * 关注
     */
    void saveUserConcern(int userid, int toUserid, ConcernType concernType);

    /**
     * 取关
     */
    void deletedUserConcern(int userid, int toUserid, ConcernType concernType);

    /**
     * 查询关注数
     */
    Integer queryConcernNums(int userid, ConcernType concernType);

    /**
     * 查询粉丝数
     */
    Integer queryAdmiresNums(int toUserid, ConcernType concernType);

    /**
     * 查询某用户的粉丝或关注列表
     */
    Page<UserInfoEntity> findConcernList(ConcernFilterBo concernFilterBo);

    /**
     * 查询该用户是否被这个用户关注
     */
    Integer findIsConcern(int userid, int toUserid, ConcernType concernType);

    /**
     * 查找某用户的粉丝
     */
    List<ConcernEntity> findConcerns(int toUserid, ConcernType concernType);
}
