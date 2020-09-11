package com.kanata.user.service.app.bo.userRelation;

import lombok.Data;

@Data
public class UserRelationBo {

    private int id;

    private int userid;

    private int friendId;

    private int type;

}
