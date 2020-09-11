package com.kanata.invitation.service.bo.userLike;

import lombok.Data;

@Data
public class UserLikeFilterBo {

    private int likeUserId;

    private int likeInvitationId;

    private int status;

    private int pageNo;

    private int pageSize;
}
