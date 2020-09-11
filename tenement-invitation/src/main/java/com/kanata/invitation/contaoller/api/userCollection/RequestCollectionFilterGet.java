package com.kanata.invitation.contaoller.api.userCollection;

import lombok.Data;

@Data
public class RequestCollectionFilterGet {

    private int userId;

    private int pageNo = 1;

    private int pageSize = 10;

}
