package com.kanata.invitation.service.bo.invitationBrowsing;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class InvitationBrowsingBo {

    private int userId;

    private int invitationId;

    private LocalDateTime browsingTime;

}
