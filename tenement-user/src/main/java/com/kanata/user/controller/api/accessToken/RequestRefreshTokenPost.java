package com.kanata.user.controller.api.accessToken;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RequestRefreshTokenPost {

    @ApiModelProperty("旧jwt")
    private String jwt;

    @ApiModelProperty("旧refreshToken")
    private String refreshToken;
}

