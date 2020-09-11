package com.kanata.message.dao.vo.privateMsg;

import lombok.Data;

@Data
public class PrivateMsgUserReceiveFilterVo {

    private Integer userid;//接收的用户id

    private int pageNo;

    private int pageSize;
}
