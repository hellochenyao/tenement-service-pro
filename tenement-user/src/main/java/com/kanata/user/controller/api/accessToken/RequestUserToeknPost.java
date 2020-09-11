package com.kanata.user.controller.api.accessToken;

import lombok.Data;

/**
 * @author chenyao
 * date 2020-09-02
 */
@Data
public class RequestUserToeknPost {

    private String telephone;

    private String smsCode;

    private String code;

}
