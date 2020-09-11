package com.kanata.invitation.service.bo.userLike;

import com.kanata.core.common.enums.UserLikeEnum;
import lombok.Data;

@Data
public class UserLikeBo {

    private int id;

    private int likeUserId;

    private int likeInvitationId;

    private int status = UserLikeEnum.PLAIN.getCode();

}
