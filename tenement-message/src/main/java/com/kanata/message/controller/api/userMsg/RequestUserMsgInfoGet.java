package com.kanata.message.controller.api.userMsg;

import lombok.Data;

@Data
public class RequestUserMsgInfoGet {
    private int userId;

    private int pageNo=1;

    private int pageSize=10;
}
