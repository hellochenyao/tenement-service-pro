package com.kanata.invitation.controller;

import com.kanata.core.common.enums.ConcernType;
import com.kanata.feign.api.user.IUserInfoFeignClient;
import com.kanata.feign.dto.FansDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chenyao
 * date 2020-09-07
 */
@Api(tags = "测试模块")
@RestController
public class TestController {

    @Autowired
    private IUserInfoFeignClient iUserInfoFeignClient;

    @ApiOperation("测试方法")
    @PostMapping(value = "/test")
    public void test(){
        FansDto rest = iUserInfoFeignClient.findFans(10000,10006, ConcernType.USER);
        System.out.println(rest);
    }

}
