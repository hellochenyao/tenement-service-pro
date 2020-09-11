package com.kanata.invitation.service.bo.tenementInvitation;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class TenementInvitationBo {

    private Integer id;

    //帖子标题
    private String title;

    //帖子内容
    private String content;

    //发布人
    private Integer userId;

    //发布时间
    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    //浏览次数
    private Integer viewTimes=0;

    //帖子类型（0 求租 1房源 2转租）
    private Integer type;

    //出租类型（0 整租 1 短租 2合租）
    private Integer roomRentType;

    //发布人
    private String publisher;

    //户型
    private String houseLayOut;

    //期望地点
    private String location;

    //房源具体位置
    private String latitude;

    //预算
    private Double rental;
    private String enterNums;

    //期望入住时间
    private LocalDate desiredDate;

    //中介免打扰 （0拒绝中介 1接收中介)
    private Integer acceptedMedium;

    //是否可以直接打电话给我 (0不可以 1可以)
    private Integer allowCallMe;

    //是否显示个人信息
    private Integer showPersonalInfo;

    private String remark;

    //房源图片
    private String housingImgs;

    //房源视频
    private String housingVideos;

    //所在城市
    private String city;
}
