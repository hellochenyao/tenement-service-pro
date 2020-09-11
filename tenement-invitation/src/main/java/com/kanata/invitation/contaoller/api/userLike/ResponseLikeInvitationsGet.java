package com.kanata.invitation.contaoller.api.userLike;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ResponseLikeInvitationsGet {

    private int likeUserId;

    private List<LikeInvitation> likeInvitations = new ArrayList<>();

    @Data
    public static class LikeInvitation{
        private int invitationId;
    }
}
