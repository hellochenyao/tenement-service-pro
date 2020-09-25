package com.kanata.user.controller.api.userinfo;

import lombok.Data;

/**
 * @author chenyao
 * date 2020-09-24
 */
@Data
public class ResponseLevelInfoPut {

    private Integer level;

    private Integer needExp;

    private Integer incExp;

    private Boolean signState;

    private Boolean isSignContinue;

}
