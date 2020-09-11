package com.kanata.message.controller.api.userMsg;

import lombok.Data;

@Data
public class RequestWordsToUserFilterGet {

    private int invitationId;

    private int pageNo=1;

    private int pageSize=10;

}
