package com.kanata.invitation.controller.api.userLike;

import lombok.Data;

@Data
public class RequestGiveLikeFilterGet {

    private int userId;

    private int pageNo = 1;

    private  int pageSize = 10;

}
