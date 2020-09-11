package com.kanata.invitation.service.bo.invitationBrowsing;

import lombok.Data;

@Data
public class UserBrowsingFilterBo {

    private int userId;

    private int invitationId;

    private Boolean isHaveResponse;

    private int pageNo;

    private int pageSize;

}
