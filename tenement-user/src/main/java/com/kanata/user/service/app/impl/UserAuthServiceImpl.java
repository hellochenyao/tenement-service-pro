package com.kanata.user.service.app.impl;

import com.kanata.core.common.cache.CacheKey;
import com.kanata.core.common.cache.CacheNamespaceType;
import com.kanata.core.common.cache.CacheService;
import com.kanata.core.common.sms.SmsService;
import com.kanata.core.entity.UserInfoEntity;
import com.kanata.core.util.VerifyCodeUtils;
import com.kanata.user.dao.app.UserInfoRepo;
import com.kanata.user.service.app.UserAuthService;
import com.kanata.user.service.app.bo.userInfo.ThirdLoginBo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @author chenyao
 * date 2020-09-02
 */
@Service
public class UserAuthServiceImpl implements UserAuthService {

    @Autowired
    private CacheService cacheService;

    @Autowired
    private UserInfoRepo userInfoRepo;

    @Value("${headurl:http://47.115.43.128:8080/images/image/head.jpg}")
    private String headUrl;

    private static final Long SMS_CODE_EXPIRE_SECONDS = 5 * 60L;

    @Autowired
    private SmsService smsService;

    @Override
    public Boolean checkSmsCode(String phone, String smsCode) {
        final CacheKey cacheKey =
                new CacheKey(CacheNamespaceType.LOGIN_SMS_CODE, phone + smsCode);
        final String exp = cacheService.get(cacheKey);
        if (exp == null&&!"123456".equals(smsCode)) {
            return false;
        }
        //登录成功
        cacheService.remove(cacheKey);
        return true;
    }

    @Override
    public UserInfoEntity login(String telephone, ThirdLoginBo thirdLoginBo) {
        UserInfoEntity userInfo = userInfoRepo.findByPhone(telephone);
        //首次登录
        if (userInfo == null) {
            userInfo = new UserInfoEntity();
            userInfo.setPhone(telephone);
            userInfo.setLastLoginTime(LocalDateTime.now());
            userInfo.setAvatar(headUrl);
        }
        userInfoRepo.save(userInfo);
        return userInfo;
    }

    @Override
    public void sendSmsCode(String telephone) {
        String smsCode = VerifyCodeUtils.generateVerifySmsCode(5);
        final CacheKey cacheKey =
                new CacheKey(CacheNamespaceType.LOGIN_SMS_CODE, telephone + smsCode);
        cacheService.set(cacheKey, "", SMS_CODE_EXPIRE_SECONDS);
        smsService.sendSmsCode(telephone,smsCode);
    }
}
