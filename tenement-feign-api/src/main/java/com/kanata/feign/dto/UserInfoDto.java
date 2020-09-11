package com.kanata.feign.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author chenyao
 * date 2020-09-07
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoDto {

    private int id;

    private String avatar;

    private String nickName;

    private int gender;

    private String phone;

    private String weChat;

    private String school;

    private String eduBack;

    private String isWorker;

    private String city;

    private String province;

    private String country;

    private String openId;

    private String unionId;

    private String lastLoginTime;

    private String createTime;

    private String occupation;

}
