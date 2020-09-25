package com.kanata.invitation.controller.api.userLike;

import com.kanata.core.common.enums.UserLikeEnum;
import lombok.Data;

@Data
public class RequestLikeInvitationsFilterGet {

    private int likeUserId;

    private int status = UserLikeEnum.LIKE.getCode();

    private int pageNo = 1;

    private int pageSize = 10;
}
