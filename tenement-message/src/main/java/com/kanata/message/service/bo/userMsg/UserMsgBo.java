package com.kanata.message.service.bo.userMsg;

import lombok.Data;

@Data
public class UserMsgBo {

    //发消息者id
    private int userId;

    //帖子
    private int invitationId;

    private String msg;

    private int time;

    private int  answerMsgId;

    private int responseUserId;
}
