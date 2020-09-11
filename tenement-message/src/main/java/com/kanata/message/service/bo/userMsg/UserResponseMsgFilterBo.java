package com.kanata.message.service.bo.userMsg;

import lombok.Data;

@Data
public class UserResponseMsgFilterBo {

    private int pid;

    private int invitationId;

    private int pageNo;

    private int pageSize;
}
