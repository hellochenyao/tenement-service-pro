package com.kanata.message.controller.api.userMsg;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class ResponseUserMsgInfoGet {
    private List<UserMsgInfo> data;

    private int total;

    @Data
    public static class UserMsgInfo{

        @ApiModelProperty("id")
        private int id;

        @ApiModelProperty("留言的用户")
        private int userId;

        @ApiModelProperty("留言的用户名")
        private String userNickName;

        @ApiModelProperty("留言的用户头像")
        private String userAvatr;

        @ApiModelProperty("留言的内容")
        private String content;

        @ApiModelProperty("回答的内容")
        private String responseContent;

        @ApiModelProperty("回答的用户")
        private int answerUserId;

        @ApiModelProperty("回答的用户名")
        private String answerUserNickName;

        private String createTime;

        private int grade;

        private int invitationId;
    }
}
