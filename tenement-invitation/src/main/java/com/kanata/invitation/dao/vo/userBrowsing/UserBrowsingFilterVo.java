package com.kanata.invitation.dao.vo.userBrowsing;

import lombok.Data;

@Data
public class UserBrowsingFilterVo {
    private int userId;

    private int invitationId;

    private Boolean isHaveResponse;

    private int pageNo;

    private int pageSize;
}
