package com.kanata.message.service.bo.privateMsg;

import lombok.Data;

@Data
public class PrivateMsgReceiveUserFilterBo {
    private Integer userid;//接收的用户id

    private int pageNo;

    private int pageSize;
}
