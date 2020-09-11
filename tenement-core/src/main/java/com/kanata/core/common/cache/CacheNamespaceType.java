package com.kanata.core.common.cache;


import com.kanata.core.common.enums.EnumsType;
import com.kanata.core.common.enums.EnumsValue;

/**
 * 缓存命名空间
 */
public enum CacheNamespaceType implements EnumsType {

    VERIFICATION_CODE_SEND_LIMIT("验证码发送限制"), LOGIN_SMS_CODE("登录验证码"),
    THIRD_LOGIN_CODE("第三方登录Code"), ADMIN_LOGIN_FAIL_COUNT("管理员登录失败次数"),
    LAST_REQUEST_TIME("用户最后请求缓存");


    private String label;

    CacheNamespaceType(String label) {
        this.label = label;
    }

    @Override
    public String getLabel() {
        return label;
    }
}
