package com.kanata.user.controller.api.userRelation;

import lombok.Data;

/**
 * @author chenyao
 * date 2020-09-09
 */
@Data
public class ResponseUserRelationGet {

    private int id;

    private int userid;

    private int friendId;

    private int type;//0待验证 1已验证 -1已拉黑

}
