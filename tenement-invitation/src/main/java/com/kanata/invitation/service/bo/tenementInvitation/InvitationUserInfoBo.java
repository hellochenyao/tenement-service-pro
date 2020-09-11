package com.kanata.invitation.service.bo.tenementInvitation;

import lombok.Data;

@Data
public class InvitationUserInfoBo {
    private Integer id;

    private String title;

    private int userId;

    private String content;

    private String publisher;

    private Integer type;

    private String location;

    private String latitude;

    private Double rental;

    private String enterNums;

    private String desiredDate;

    private Integer showPersonalInfo;

    private Integer acceptedMedium;

    private Integer roomRentType;

    private String houseLayout;

    private Integer allowCallMe;

    private String weChat;

    private String school;

    private String grade;

    private String remark;

    private String housingImgs;

    private String housingVideos;

    private Integer viewTimes;

    private String updateTime;

    private String createTime;

    private String city;

    private String lastLoginTime;

    private String avatar;

    private int gender;

    private Integer supportNums=0;

    private int leaveMsgNums;

    private Integer status;

    private Integer likeStatus;
}
