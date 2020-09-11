package com.kanata.user.service.app;

import com.kanata.core.entity.UserInfoEntity;
import com.kanata.user.service.app.bo.userInfo.ThirdLoginBo;

/**
 * @author chenyao
 * date 2020-09-02
 */
public interface UserAuthService {

    Boolean checkSmsCode(String phone,String smsCode);

    UserInfoEntity login(String telephone, ThirdLoginBo thirdLoginBo);

    void sendSmsCode(String telephone);

}
