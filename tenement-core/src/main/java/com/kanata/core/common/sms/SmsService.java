package com.kanata.core.common.sms;

/**
 * @author chenyao
 * date 2020-09-03
 */
public interface SmsService {

    /**
     * 发送短信验证码
     * @param telephone
     * @param smsCode
     */
    void sendSmsCode(String telephone,String smsCode);

}
