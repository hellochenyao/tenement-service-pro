package com.pro.springcloud.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * User: cy
 * Date: 2020/3/5
 */
@RestController
@RefreshScope
public class ProController {

    @Value("${data.env.user}")
    private String userName;

    @RequestMapping(value = "/get",method = RequestMethod.GET)
    public String getPro(){
        return "aaa" + userName;
    }

}
