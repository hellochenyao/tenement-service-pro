package com.kanata.core.common.sms.impl;

import com.alibaba.fastjson.JSONObject;
import com.kanata.core.common.sms.SmsService;
import com.kanata.core.common.sms.valueobj.SmsConfig;
import com.kanata.core.common.sms.valueobj.TencentYunSmsAccessKey;
import com.kanata.core.exception.SendSmsException;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.common.profile.Language;
import com.tencentcloudapi.cvm.v20170312.CvmClient;
import com.tencentcloudapi.cvm.v20170312.models.DescribeInstancesRequest;
import com.tencentcloudapi.cvm.v20170312.models.DescribeInstancesResponse;
import com.tencentcloudapi.cvm.v20170312.models.Filter;
import com.tencentcloudapi.sms.v20190711.SmsClient;
import com.tencentcloudapi.sms.v20190711.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20190711.models.SendSmsResponse;
import com.tencentcloudapi.sms.v20190711.models.SendStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author chenyao
 * date 2020-09-03
 */
@Service
@Slf4j
public class SmsServiceImpl implements SmsService {

    @Autowired
    private TencentYunSmsAccessKey accessKey;

    @Autowired
    private SmsConfig smsConfig;

    private static final String IN_DEED_PHONE_NO = "86";

    @Override
    public void sendSmsCode(String telephone, String smsCode) {
        try {
            // 实例化一个认证对象，入参需要传入腾讯云账户secretId，secretKey,此处还需注意密钥对的保密
            Credential cred = new Credential(accessKey.getSecretId(), accessKey.getSecretKey());

            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setEndpoint("sms.tencentcloudapi.com");

            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setHttpProfile(httpProfile);

            SmsClient client = new SmsClient(cred, "", clientProfile);

            smsConfig.setPhoneNumberSet(new String[]{IN_DEED_PHONE_NO + telephone});
            smsConfig.setTemplateParamSet(new String[]{smsCode});
            String params = initRespFilter(smsConfig);
            SendSmsRequest req = SendSmsRequest.fromJsonString(params, SendSmsRequest.class);

            SendSmsResponse resp = client.SendSms(req);
            if (resp.getSendStatusSet() != null && resp.getSendStatusSet().length > 0) {
                SendStatus res = resp.getSendStatusSet()[0];
                if (!"Ok".equals(res.getCode())) {
                    log.error("短信验证码发送失败：{}，手机号：{}", res.getMessage(), res.getPhoneNumber());
                    throw new SendSmsException(SendSmsException.CodeOption.SEND_SMS_CODE_ERROR);
                }
            }
        } catch (TencentCloudSDKException e) {
            log.error("短信验证码发送失败:{} {} {}", e.getMessage(), accessKey, smsConfig);
            throw new SendSmsException(SendSmsException.CodeOption.SEND_SMS_CODE_ERROR);
        }
    }

    /**
     * 封装请求腾讯云短信服务的参数
     *
     * @param config
     * @return
     */
    private String initRespFilter(SmsConfig config) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("PhoneNumberSet", config.getPhoneNumberSet());
        jsonObject.put("TemplateID", config.getTemplateID());
        jsonObject.put("SmsSdkAppid", config.getSmsSdkAppid());
        jsonObject.put("Sign", config.getSign());
        jsonObject.put("TemplateParamSet", config.getTemplateParamSet());
        return jsonObject.toJSONString();
    }

}
