package com.kanata.invitation.controller.api.userLike;

import lombok.Data;

@Data
public class ResponseUserLikeGet {
    private int id;

    private int likeUserId;

    private int likeInvitationId;

    private int status;
}
