package com.pro.springcloud.controller;

import com.pro.springcloud.service.ProConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * User: cy
 * Date: 2020/3/5
 */
@RestController
public class ProConsumerController {

    @Autowired
    private ProConsumer proConsumer;

    @RequestMapping(value = "/pro",method = RequestMethod.GET)
    public String consum(){
        return proConsumer.getPro();
    }
}
