package com.kanata.core.common.enums;

import lombok.AllArgsConstructor;

/**
 * @author: chizhihong
 * @create: 2019/08/01
 * @version: 1.0
 **/
@AllArgsConstructor
public enum OprUserType implements EnumsType {

    USER("用户"),
    ADMIN("管理员");

    private String label;

    @Override
    public String getLabel() {
        return label;
    }
}
