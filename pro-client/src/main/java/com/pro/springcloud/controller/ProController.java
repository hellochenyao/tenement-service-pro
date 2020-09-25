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

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(()->{
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("t1");
        });
        Thread t2 = new Thread(()->{
            System.out.println("t2");
        });
        t1.start();
        t1.join();
        t2.start();
        t2.join();
    }
}
