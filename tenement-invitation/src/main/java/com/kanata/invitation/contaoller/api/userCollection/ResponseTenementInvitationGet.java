package com.kanata.invitation.contaoller.api.userCollection;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class ResponseTenementInvitationGet {

    private List<TenementInvitation> data;

    private Integer total;

    @Data
    public static class TenementInvitation {
        @ApiModelProperty("帖子id")
        private Integer id;

        @ApiModelProperty("帖子标题")
        private String title;

        @ApiModelProperty("用户id")
        private int userId;

        @ApiModelProperty("帖子内容")
        private String content;

        @ApiModelProperty("发布人")
        private String publisher;

        @ApiModelProperty("期望地点")
        private String location;

        private String latitude;

        @ApiModelProperty("预算")
        private Double rental;

        @ApiModelProperty("入住人数")
        private String enterNums;

        @ApiModelProperty("期望入住时间")
        private String desiredDate;

        @ApiModelProperty("中介免打扰 （0拒绝中介 1接收中介)")
        private Integer acceptedMedium;

        @ApiModelProperty("出租类型（0 整租 1 短租 2合租）")
        private Integer roomRentType;

        @ApiModelProperty("是否可以直接打电话给我 (0不可以 1可以)")
        private Integer allowCallMe;

        @ApiModelProperty("你的微信号")
        private String weChat;

        @ApiModelProperty("发布人学校名字")
        private String school;

        @ApiModelProperty("发布人年级")
        private String grade;

        private String remark;

        @ApiModelProperty("房源图片")
        private String housingImgs;

        @ApiModelProperty("房源视频")
        private String housingVideos;

        @ApiModelProperty("浏览次数")
        private Integer viewTimes;

        @ApiModelProperty("更新时间")
        private String updateTime;

        @ApiModelProperty("创建时间")
        private String createTime;

        @ApiModelProperty("所在城市")
        private String city;

        @ApiModelProperty("最后一次登录时间")
        private String lastLoginTime;

        @ApiModelProperty("头像")
        private String avatar;

        @ApiModelProperty("性别")
        private int gender;

        @ApiModelProperty("点赞数")
        private Integer supportNums=0;

        @ApiModelProperty("留言数")
        private int leaveMsgNums;

        private Integer status;
    }
}
