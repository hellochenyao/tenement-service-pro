package com.kanata.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author chenyao
 * date 2020-08-21
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.kanata"})
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.kanata")
@EnableCircuitBreaker
@EnableSwagger2
@EntityScan("com.kanata")
@EnableJpaRepositories("com.kanata")
public class TenementUserApp {
    public static void main(String[] args) {
        SpringApplication.run(TenementUserApp.class,args);
    }
}
