package com.kanata.message.controller.api.dynamic;

import lombok.Data;

@Data
public class RequestDynamicFilterGet {

    private int type=0;
    private int dynamicId;
    private int pageNo = 1;
    private int pageSize = 10;

}
