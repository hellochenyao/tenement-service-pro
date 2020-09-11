package com.kanata.invitation.dao;


import com.kanata.core.dto.page.Page;
import com.kanata.invitation.dao.vo.userLike.GiveUserLikeFilterVo;
import com.kanata.invitation.dao.vo.userLike.GiveUserLikeVo;

public interface UserLikeDao {

    public Page<GiveUserLikeVo> findGiveLikeUser(GiveUserLikeFilterVo filterVo);

}
