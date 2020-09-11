package com.kanata.feign.config;

import com.kanata.core.common.enums.ConcernTypeConverter;
import feign.Contract;
import feign.Logger;
import feign.codec.Encoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.AnnotatedParameterProcessor;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.cloud.openfeign.support.SpringMvcContract;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.format.support.FormattingConversionService;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: fy
 * @date: 2020/02/28 11:13
 **/
@Configuration
public class FeignSupportConfig {

    @Autowired
    private ObjectFactory<HttpMessageConverters> httpMessageConvertersObject;

    @Autowired(required = false)
    private List<AnnotatedParameterProcessor> parameterProcessors = new ArrayList<>();

//    /**
//     * feign 传递实体类或者文件时传输转化工具
//     */
//    @Bean
//    @Primary
//    @Scope("prototype")
//    public Encoder multipartFormEncoder() {
//        return new SpringFormEncoder(new SpringEncoder(httpMessageConvertersObject));
//    }

    /**
     * feign请求日志级别
     *
     * @return RequestInterceptor
     */
    @Bean
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    @Bean
    public Contract feignContract(FormattingConversionService feignConversionService) {
        //在原配置类中是用ConversionService类型的参数，但ConversionService接口不支持addConverter操作，使用FormattingConversionService仍然可以实现feignContract配置。
        feignConversionService.addConverter(new ConcernTypeConverter());
        return new SpringMvcContract(this.parameterProcessors, feignConversionService);
    }

}
