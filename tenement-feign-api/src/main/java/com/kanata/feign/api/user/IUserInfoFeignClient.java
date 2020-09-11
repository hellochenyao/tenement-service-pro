package com.kanata.feign.api.user;

import com.kanata.core.common.enums.ConcernType;
import com.kanata.feign.config.FeignSupportConfig;
import com.kanata.feign.constant.FeignConstant;
import com.kanata.feign.dto.FansDto;
import com.kanata.feign.dto.Rest;
import com.kanata.feign.dto.UserInfoDto;
import com.kanata.feign.hystrix.user.IUserInfoFeignClientFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

/**
 * @author chenyao
 * date 2020-09-04
 */
@Service
@FeignClient(name = FeignConstant.USER_APPLICATION_NAME,
        fallbackFactory = IUserInfoFeignClientFallbackFactory.class)
public interface IUserInfoFeignClient {

    @RequestMapping(value = "/app/user/concern/{userId}/find/user/fans",method = RequestMethod.GET)
    FansDto findFans(@PathVariable("userId") int userId, @RequestParam("toUserid") int toUserid,@RequestParam("concernType") ConcernType concernType);

    @GetMapping(value = "/app/user/{userId}/info")
    UserInfoDto findUserInfo(@PathVariable("userId") int userId);

}
