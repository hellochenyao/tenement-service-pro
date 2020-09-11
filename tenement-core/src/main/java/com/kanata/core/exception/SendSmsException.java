package com.kanata.core.exception;

/**
 * @author chenyao
 * date 2020-09-03
 */
public class SendSmsException extends BusinessException {
    public SendSmsException(CodeOption codeOption) {
        super(codeOption.name(), codeOption.msg);
    }

    public SendSmsException(CodeOption codeOption, String... params) {
        super(codeOption.name(), String.format(codeOption.msg, params));
    }

    public enum CodeOption {

        TENCENT_SMS_PARAM_ERROR("请求短信服务参数有误"),
        SEND_SMS_CODE_ERROR("短信验证码发送失败");

        CodeOption(String msg) {
            this.msg = msg;
        }

        private String msg;

    }

}
