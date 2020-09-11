package com.kanata.invitation.contaoller.api.userLike;

import lombok.Data;

import java.util.List;

@Data
public class ResponseLikeUsersGet {

    private int likeInvitationId;

    private List<LikeUser> likeUsers;

    @Data
    public static class LikeUser{

        private int likeUserId;

        private String userName;

    }
}
