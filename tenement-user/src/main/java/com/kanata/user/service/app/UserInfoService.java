package com.kanata.user.service.app;


import com.kanata.core.entity.UserInfoEntity;
import com.kanata.user.dao.app.vo.UserInfoVo;
import com.kanata.user.service.app.bo.userInfo.UserLoginBo;
import com.kanata.user.service.app.bo.userInfo.UserModifyBo;
import org.springframework.data.domain.Page;

/**
 * 微信相关 service
 * Created by mumu on 2019/3/27.
 */
public interface UserInfoService {

    Page<UserInfoEntity> findFriends(String content, int pageNo, int pageSize);

    UserLoginBo login(String jsCode);

    UserInfoVo wxInfo(String encryptedData, String iv);

    void wxPhoneNum(String encryptedData, String iv);

    UserInfoVo info(int userId);

    void modify(UserModifyBo userModifyBo);

    void updateLastLoginTime(int id);



}
