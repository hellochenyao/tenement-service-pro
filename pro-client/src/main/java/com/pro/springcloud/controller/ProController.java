package com.pro.springcloud.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * User: cy
 * Date: 2020/3/5
 */
@RestController
public class ProController {
    @RequestMapping(value = "/get",method = RequestMethod.GET)
    public String getPro(){
        return "t";
    }
}
