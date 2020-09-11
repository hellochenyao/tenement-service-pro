package com.kanata.user.service.app;


import com.kanata.core.dto.page.Page;
import com.kanata.user.service.app.bo.userInfo.UserInfoBo;
import com.kanata.user.service.app.bo.userRelation.UserRelationBo;
import com.kanata.user.service.app.bo.userRelation.UserRelationFilterBo;

public interface UserRelationService {

    void createFriend(UserRelationBo userRelationBo);

    Page<UserInfoBo> findFriend(UserRelationFilterBo userRelationFilterBo);

    void deleteFriend(int userid, int friendId);
}
