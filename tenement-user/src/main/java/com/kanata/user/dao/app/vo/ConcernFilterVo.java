package com.kanata.user.dao.app.vo;

import com.kanata.core.common.enums.ConcernType;
import lombok.Data;

@Data
public class ConcernFilterVo {
    private Integer userid;
    private int type;
    private ConcernType concernType;
    private int pageNo;
    private int pageSize;
}
