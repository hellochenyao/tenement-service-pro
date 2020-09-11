package com.pro.springcloud.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * User: cy
 * Date: 2020/3/5
 */
@FeignClient(name = "eureka-client")
public interface ProConsumer {
    @RequestMapping(value = "/get",method = RequestMethod.GET)
    String getPro();
}
