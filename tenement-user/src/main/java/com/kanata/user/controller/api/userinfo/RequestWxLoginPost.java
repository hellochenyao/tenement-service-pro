package com.kanata.user.controller.api.userinfo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by mumu on 2019/4/16.
 */

@Data
@ApiModel("获取微信用户信息")
public class RequestWxLoginPost {

    @ApiModelProperty("jscode")
    private String jscode;
}
