package com.pro.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * User: cy
 * Date: 2020/3/5
 */
@SpringBootApplication
@EnableEurekaClient
public class ProClient {
    public static void main(String[] args) {
        SpringApplication.run(ProClient.class,args);
    }
}
