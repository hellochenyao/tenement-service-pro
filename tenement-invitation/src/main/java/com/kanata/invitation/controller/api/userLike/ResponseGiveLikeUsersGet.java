package com.kanata.invitation.controller.api.userLike;

import lombok.Data;

import java.util.List;

@Data
public class ResponseGiveLikeUsersGet {

    private List<UserInfo> data;

    private int total;

    @Data
    public static class UserInfo {

        private int userId;

        private String nickName;

        private String avatar;

        private String status;

        private String updateTime;

    }

}
