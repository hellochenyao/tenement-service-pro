package com.kanata.invitation.contaoller.api.userLike;

import lombok.Data;

@Data
public class ResponseUserLikeGet {
    private int id;

    private int likeUserId;

    private int likeInvitationId;

    private int status;
}
