package com.kanata.message.service.bo.privateMsg;

import lombok.Data;

@Data
public class PrivateMsgFilterBo {

    //发送方用户id
    private Integer userid;

    //接收方用户id
    private Integer receiveUserid;

    private String creteTime;

    private int pageNo;

    private int pageSize;

}
