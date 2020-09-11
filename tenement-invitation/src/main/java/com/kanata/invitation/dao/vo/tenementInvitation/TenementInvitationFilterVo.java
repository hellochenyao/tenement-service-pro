package com.kanata.invitation.dao.vo.tenementInvitation;

import lombok.Data;

@Data
public class TenementInvitationFilterVo {

    private Integer ascending;

    private Integer type;

    private String title;

    private int publisherId;

    //所在城市
    private String city;

    private String location;

    private Integer pageNo;

    private Integer pageSize;

}
