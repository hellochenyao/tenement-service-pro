package com.kanata.user.service.app.bo.userInfo;

import lombok.Data;

/**
 * Created by mumu on 2019/3/27.
 */
@Data
public class UserLoginBo {

    private String sessionKey;
    private String openId;
    private int userId;
    private int isNew;

}
