package com.pro.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.RestController;

/**
 * User: cy
 * Date: 2020/3/5
 */
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class EurekaCosumer {
    public static void main(String[] args) {
        SpringApplication.run(EurekaCosumer.class,args);
    }
}
