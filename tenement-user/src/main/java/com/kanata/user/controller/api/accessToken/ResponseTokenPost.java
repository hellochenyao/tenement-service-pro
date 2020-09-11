package com.kanata.user.controller.api.accessToken;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ResponseTokenPost {

    @ApiModelProperty("登录时生成jwt,有效期30分钟")
    private String jwt;

    @ApiModelProperty("登录时生成refreshToken")
    private String refreshToken;
}
