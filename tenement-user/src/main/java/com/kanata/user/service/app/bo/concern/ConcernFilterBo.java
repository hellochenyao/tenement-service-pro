package com.kanata.user.service.app.bo.concern;

import com.kanata.core.common.enums.ConcernType;
import lombok.Data;

@Data
public class ConcernFilterBo {
    private Integer userid;
    private int type;
    private ConcernType concernType;
    private int pageNo;
    private int pageSize;
}
