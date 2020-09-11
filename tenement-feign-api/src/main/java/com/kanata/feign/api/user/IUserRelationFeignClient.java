package com.kanata.feign.api.user;

import com.kanata.feign.constant.FeignConstant;
import com.kanata.feign.dto.UserRelationDto;
import com.kanata.feign.hystrix.user.IUserRelationFeignClientFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author chenyao
 * date 2020-09-09
 */
@FeignClient(name = FeignConstant.USER_APPLICATION_NAME,fallbackFactory = IUserRelationFeignClientFallbackFactory.class)
public interface IUserRelationFeignClient {

    @GetMapping(value = "/app/user/relation/{userId}")
    UserRelationDto findRelation(@PathVariable("userId") int userId, @RequestParam("friendId") int friendId);

}
