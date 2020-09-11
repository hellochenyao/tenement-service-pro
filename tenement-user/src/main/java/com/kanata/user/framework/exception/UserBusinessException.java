package com.kanata.user.framework.exception;

import com.kanata.core.exception.BusinessException;

public class UserBusinessException extends BusinessException {
    public UserBusinessException(CodeOption codeOption) {
        super(codeOption.name(), codeOption.msg);
    }

    public UserBusinessException(CodeOption codeOption, String... params) {
        super(codeOption.name(), String.format(codeOption.msg, params));
    }

    public enum CodeOption {

        VERIFY_CODE_WRONG("code无效"),
        VERIFY_SMS_CODE_WRONG("短信验证码错误"),
        SEND_TOO_FREQUENT("发送太频繁"),
        VERIFY_TELEPHONE("手机号格式不正确"),
        USER_NOT_EXIST("用户不存在"),
        TELEPHONE_EXIST("手机号已存在"),
        USER_BIND_INFO_NOT_EXIST("用户绑定信息不存在"),
        USER_BIND_INFO_EXIST("用户绑定信息已存在"),
        USER_BANNED("您的账号已被封禁，请联系客服"),
        USER_EXHIBITION_ID_EXIST("用户展示ID已存在"),
        USERNAME_EXIST("用户名已存在"),
        REPORT_NOT_EXIST("举报不存在"),
        RESUME_NOT_EXIST("履历不存在"),
        CREDENTIALS_EXIST("该游戏已存在游戏凭证"),
        CREDENTIALS_NOT_EXIST("游戏凭证不存在"),
        RESUME_ALREDY_EXIST("该游戏已存在履历"), SEND_SMS_FAIL("短信发送失败"),
        IMAGE_FORMAT_ERROR("图片格式不正确"),
        URL_NOT_MATCH("图片域名与路径不匹配"),
        IMAGE_ERROR("图片错误"),;


        CodeOption(String msg) {
            this.msg = msg;
        }

        private String msg;

    }
}
