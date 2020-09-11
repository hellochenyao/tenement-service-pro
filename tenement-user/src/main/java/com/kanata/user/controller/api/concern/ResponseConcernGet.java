package com.kanata.user.controller.api.concern;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ResponseConcernGet {

    @ApiModelProperty("关注数")
    private int concernNums;

    @ApiModelProperty("粉丝数")
    private int admireNums;

}
