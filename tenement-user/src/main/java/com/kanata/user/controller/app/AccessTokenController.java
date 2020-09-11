package com.kanata.user.controller.app;

import com.kanata.core.dto.jwt.JwtDataDto;
import com.kanata.core.dto.jwt.JwtDto;
import com.kanata.core.entity.UserInfoEntity;
import com.kanata.core.exception.IllegalParameterException;
import com.kanata.core.util.JwtUtils;
import com.kanata.user.controller.api.accessToken.RequestRefreshTokenPost;
import com.kanata.user.controller.api.accessToken.RequestUserToeknPost;
import com.kanata.user.controller.api.accessToken.ResponseTokenPost;
import com.kanata.user.framework.exception.UserBusinessException;
import com.kanata.user.service.app.UserAuthService;
import com.kanata.user.service.app.bo.userInfo.ThirdLoginBo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author chenyao
 * date 2020-09-02
 */
@Api(tags = "APP-权限访问认证")
@RestController
@RequestMapping("/access_tokens")
public class AccessTokenController {

    @Autowired
    private UserAuthService userAuthService;

    @ApiOperation(value = "获取用户身份认证Token", notes = "三种入参形式：1只传code表示第三方登录；2只传telephone和smsCode表示手机验证码登录；3三者都传表示登录绑定第三方账号")
    @PostMapping("/users")
    public ResponseTokenPost createToken(@RequestBody RequestUserToeknPost request) {
        //登录手机号
        final String phone = request.getTelephone();
        //登录短信验证码
        final String smsCode = request.getSmsCode();
        if (StringUtils.isNotBlank(phone) && StringUtils.isNotBlank(smsCode)) {
            Boolean checkState = userAuthService.checkSmsCode(phone, smsCode);
            //判断redis是否存在该验证码
            if (!checkState) {
                throw new UserBusinessException(UserBusinessException.CodeOption.VERIFY_SMS_CODE_WRONG);
            }
            ThirdLoginBo thirdLoginBo = null;
            UserInfoEntity userInfo = userAuthService.login(phone, thirdLoginBo);
            ResponseTokenPost response = new ResponseTokenPost();
            JwtDto jwtDto = userInfo.assignToken();
            response.setJwt(jwtDto.getToken());
            response.setRefreshToken(jwtDto.getRefreshToken());
            return response;
        }
        //其他
        throw new IllegalParameterException(IllegalParameterException.CodeOption.INVALID_PARAMETER);
    }

    @ApiOperation(value = "发送登陆验证码")
    @PostMapping(value = "/{phone}/sms-code")
    public void sendCode(@PathVariable String phone) {
        userAuthService.sendSmsCode(phone);
    }

    @ApiOperation(value = "用户刷新登陆权鉴", notes = "根据旧jwt和refreshToken生成新权鉴")
    @RequestMapping(value = "/refreshToken", method = RequestMethod.POST)
    public ResponseTokenPost refreshToken(@RequestBody RequestRefreshTokenPost request) {
        ResponseTokenPost response = new ResponseTokenPost();
        //refreshToken是jwt通过md5进行加密 用于签名判断和jwt是否匹配
        JwtDataDto jwtDataDto = JwtUtils.parseToken(request.getJwt(), request.getRefreshToken());
        JwtDto jwtDto = JwtUtils.generateJwt(jwtDataDto);
        response.setJwt(jwtDto.getToken());
        response.setRefreshToken(jwtDto.getRefreshToken());
        return response;
    }

}
