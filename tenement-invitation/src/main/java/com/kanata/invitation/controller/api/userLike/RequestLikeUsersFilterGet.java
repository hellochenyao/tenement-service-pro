package com.kanata.invitation.controller.api.userLike;

import com.kanata.core.common.enums.UserLikeEnum;
import lombok.Data;

@Data
public class RequestLikeUsersFilterGet {

    private int likeInvitationId;

    private int status = UserLikeEnum.LIKE.getCode();

    private int pageNo = 1;

    private int pageSize = 10;
}
