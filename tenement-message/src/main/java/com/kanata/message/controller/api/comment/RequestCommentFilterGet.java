package com.kanata.message.controller.api.comment;

import lombok.Data;

@Data
public class RequestCommentFilterGet {

    private Integer dynamicId;

    private Integer messageId;

    private int pageNo = 1;

    private int pageSize = 10;

}
