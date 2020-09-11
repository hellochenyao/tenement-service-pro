package com.kanata.user.controller.api.userRelation;

import lombok.Data;

@Data
public class RequestUserRelationFilterGet {
    private int userid;
    private int type;
    private int pageNo=1;
    private int pageSize=10;
}
