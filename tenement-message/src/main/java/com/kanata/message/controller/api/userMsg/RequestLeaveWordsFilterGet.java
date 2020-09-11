package com.kanata.message.controller.api.userMsg;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RequestLeaveWordsFilterGet {

    @ApiModelProperty("帖子id")
    private int invitationId;

    private int pageNo = 1 ;

    private int pageSize = 10;
}
