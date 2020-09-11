package com.kanata.user.service.app.bo.userInfo;

import lombok.Data;

/**
 * Created by mumu on 2019/4/16.
 */

@Data
public class UserTargetBo {

    private int userId;

    private String targetMsg;


    public UserTargetBo() {
    }

    public UserTargetBo(int userId, String targetMsg) {
        this.userId = userId;
        this.targetMsg = targetMsg;
    }
}
