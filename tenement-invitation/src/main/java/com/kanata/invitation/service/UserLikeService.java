package com.kanata.invitation.service;


import com.kanata.core.dto.page.Page;
import com.kanata.core.entity.UserLikeEntity;
import com.kanata.invitation.dao.vo.userLike.GiveUserLikeVo;
import com.kanata.invitation.service.bo.userLike.GiveUserLikeFilterBo;
import com.kanata.invitation.service.bo.userLike.UserLikeBo;
import com.kanata.invitation.service.bo.userLike.UserLikeFilterBo;

public interface UserLikeService {

    void save(UserLikeBo userLikebo);

    /**
     * 查询该帖子的所有点赞用户
     */
    Page<UserLikeEntity> getUserLikeListByInvitationId(UserLikeFilterBo userLikeFilterBo);

    /**
     * 查询该用户的所有点赞帖子
     */
    Page<UserLikeEntity> getInvitationLikeListByUserId(UserLikeFilterBo userLikeFilterBo);

    UserLikeBo findUserLikeByInvitationIdAndUserId(int invitationId, int userId);

    void updateInvitationSupportNums(int invitationId, int userId, int likeStatus);

    Page<GiveUserLikeVo> getUserGiveLike(GiveUserLikeFilterBo filterBo);

}
