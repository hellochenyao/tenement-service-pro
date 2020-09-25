package com.kanata.message.controller.api.privateMsg;

import lombok.Data;

import java.util.List;

@Data
public class ResponseReceiveMsgGet {
    private List<Message> messages;

    private int total;

    @Data
    public static class Message{
        private String content;

        private Integer fromUserid;

        private String fromUserAvatar;

        private String fromUserNickName;

        private String descText;

        private int noReadNums;

        private int isRead;//-1未读 0已读

        private String createTime;

        private String type;
    }
}
