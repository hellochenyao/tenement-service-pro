package com.kanata.user.dao.app.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kanata.core.dto.user.Level;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserInfoVo {

    private int id;

    private String avatar;

    private String nickName;

    private int gender;

    private String phone;

    private String selfTarget;

    private int total_step;

    private String city;

    private String province;

    private String country;

    private String openId;

    private String unionId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime lastLoginTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

    private String occupation;

    private String weChat;

    private String school;

    /**
     * 积分
     */
    private Long integral;

    /**
     * 总经验 1-100 1-10 100-1000 10-20  1000-10000 20-30 10000-
     */
    private Level level;

}
