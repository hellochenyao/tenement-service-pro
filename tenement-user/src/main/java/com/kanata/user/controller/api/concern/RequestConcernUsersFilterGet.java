package com.kanata.user.controller.api.concern;

import com.kanata.core.common.enums.ConcernType;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class RequestConcernUsersFilterGet {
    @NotNull(message = "用户ID不能为空")
    private Integer userid;
    @NotNull(message = "关注类型不能为空")
    private ConcernType concernType;
    private int pageNo=1;
    private int pageSize=10;
}
