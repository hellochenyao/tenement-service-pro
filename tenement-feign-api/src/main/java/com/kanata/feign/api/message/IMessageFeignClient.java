package com.kanata.feign.api.message;

import com.kanata.feign.constant.FeignConstant;
import com.kanata.feign.hystrix.message.IMessageFeignClientFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author chenyao
 * date 2020-09-08
 */
@FeignClient(name = FeignConstant.MESSAGE_APPLICATION_NAME,fallbackFactory = IMessageFeignClientFallbackFactory.class
)
public interface IMessageFeignClient {

    @GetMapping(value = "/message/nums/{invitationId}")
    Integer findResponseMsgNums(@PathVariable("invitationId") int invitationId);

}
