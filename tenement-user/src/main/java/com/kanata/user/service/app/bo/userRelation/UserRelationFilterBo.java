package com.kanata.user.service.app.bo.userRelation;

import lombok.Data;

@Data
public class UserRelationFilterBo {

    private int userid;

    private int type;

    private int pageNo;

    private int pageSize;

}
