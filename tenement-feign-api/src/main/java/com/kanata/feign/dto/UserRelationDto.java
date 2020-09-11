package com.kanata.feign.dto;

import lombok.Data;

/**
 * @author chenyao
 * date 2020-09-09
 */
@Data
public class UserRelationDto {

    private int id;

    private int userid;

    private int friendId;

    private int type;//0待验证 1已验证 -1已拉黑

}
