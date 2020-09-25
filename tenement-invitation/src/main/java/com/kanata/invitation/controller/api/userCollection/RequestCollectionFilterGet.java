package com.kanata.invitation.controller.api.userCollection;

import lombok.Data;

@Data
public class RequestCollectionFilterGet {

    private int userId;

    private int pageNo = 1;

    private int pageSize = 10;

}
