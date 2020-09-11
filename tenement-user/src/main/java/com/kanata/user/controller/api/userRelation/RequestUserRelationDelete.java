package com.kanata.user.controller.api.userRelation;

import lombok.Data;

@Data
public class RequestUserRelationDelete {
    private int userid;
    private int friendId;
}
