package com.kanata.user.service.app.bo.userInfo;

import lombok.Data;

/**
 * Created by mumu on 2019/3/27.
 */
@Data
public class JwtStorageBo {

    private String sessionKey;
    private String openId;
    private int userId;

}
