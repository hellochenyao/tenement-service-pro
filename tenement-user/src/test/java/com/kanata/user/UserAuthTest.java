package com.kanata.user;

import com.kanata.core.common.enums.ConcernType;
import com.kanata.core.common.sms.SmsService;
import com.kanata.core.util.VerifyCodeUtils;
import com.kanata.feign.api.user.IUserInfoFeignClient;
import com.kanata.feign.dto.Rest;
import com.kanata.user.controller.api.accessToken.RequestUserToeknPost;
import com.kanata.user.controller.api.accessToken.ResponseTokenPost;
import com.kanata.user.controller.app.AccessTokenController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author chenyao
 * date 2020-09-02
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes={TenementUserApp.class})
public class UserAuthTest {

    @Autowired
    private AccessTokenController accessTokenController;

    @Autowired
    private SmsService smsService;

    @Test
    public void testLogin(){
        RequestUserToeknPost request = new RequestUserToeknPost();
        request.setTelephone("13959768999");
        request.setSmsCode("1230");
        ResponseTokenPost responseTokenPost = accessTokenController.createToken(request);
        System.out.println(responseTokenPost);
    }

    @Test
    public void testSms(){
        String smsCode = VerifyCodeUtils.generateVerifySmsCode(5);
        smsService.sendSmsCode("8613959748379",smsCode);
    }

    @Test
    public void testFei(){
//        Rest rest = userInfoFeignClient.findFans(10000, ConcernType.USER);
//        System.out.println(rest);
    }
}
