package com.kanata.invitation;

import com.kanata.feign.config.FeignSupportConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author chenyao
 * date 2020-09-04
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.kanata"})
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.kanata",defaultConfiguration = FeignSupportConfig.class)
@EnableCircuitBreaker
@EnableSwagger2
@EntityScan("com.kanata")
@EnableJpaRepositories("com.kanata")
@EnableHystrix
public class TenementInvitationApp {
    public static void main(String[] args) {
        SpringApplication.run(TenementInvitationApp.class,args);
    }
}
