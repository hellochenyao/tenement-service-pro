package com.kanata.user.service.app.bo.userInfo;

import lombok.Data;

/**
 * @author chenyao
 * date 2020-09-24
 */
@Data
public class LevelInfoBo {

    private Integer level;

    private Integer needExp;

    private Integer incExp;

    private Boolean signState;

    private Boolean isSignContinue;

}
