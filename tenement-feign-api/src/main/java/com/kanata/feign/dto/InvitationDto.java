package com.kanata.feign.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author chenyao
 * date 2020-09-08
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvitationDto {

    private Integer id;

    //帖子标题
    private String title;

    //帖子内容
    private String content;

    //发布人
    private String publisher;

    //帖子类型（0 求租 1房源 2转租）
    private Integer type;

    private Integer userId;

    //------------------------无房帖子信息---------------------------
    //是否显示个人信息
    private Integer showPersonalInfo;

    //中介免打扰 （0拒绝中介 1接收中介)
    private Integer acceptedMedium;

    //是否可以直接打电话给我 (0不可以 1可以)
    private Integer allowCallMe;

    //------------------------有房帖子信息---------------------------
    //户型
    private String houseLayOut;

    //出租类型（0 整租 1 主卧 2次卧 3床位）
    private Integer roomRentType;

    //------------------------帖子一些公共信息---------------------------
    private String remark;

    //房源图片
    private String housingImgs;

    //房源视频
    private String housingVideos;

    //房源位置 或 期望入住地点
    private String location;

    //房源具体位置(经纬度）
    private String latitude;

    //租金
    private double rental;

    private String enterNums;

    //期望入住时间 日期
    private LocalDate desiredDate;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    //浏览次数
    private Integer viewTimes=0;

    //状态
    private Integer status;

    //顶的数量
    private Integer supportNums=0;

    //所在城市
    private String city;

}
