package com.kanata.user.service.app;

import com.katana.wx.weapp.user.response.SessionKeyResponse;

/**
 * Created by mumu on 2019/3/27.
 */
public interface WxAuthService {

    SessionKeyResponse getSessionKey(String appid, String secret, String jsCode);

}
