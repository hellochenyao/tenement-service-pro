package com.kanata.invitation.contaoller.api.invitation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RequestTenementInvitationGet {

    @ApiModelProperty("升序 0 降序 1")
    private Integer ascending = 1;

    @ApiModelProperty("帖子类型 (0求租 1房源 2转租 默认为求租帖子)")
    private Integer type = 0;

    @ApiModelProperty("帖子标题")
    private String title;

    @ApiModelProperty("所在城市")
    private String city;

    private String location;

    @ApiModelProperty("发布人id")
    private int publisherId;

    private Integer pageNo = 1;

    private Integer pageSize = 10;
}
