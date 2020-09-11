package com.kanata.feign.hystrix.message;

import com.kanata.feign.api.message.IMessageFeignClient;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author chenyao
 * date 2020-09-08
 */
@Component
@Slf4j
public class IMessageFeignClientFallbackFactory implements FallbackFactory<IMessageFeignClient> {
    @Override
    public IMessageFeignClient create(Throwable throwable) {
        log.error("IMessageFeignClient处理超时：{}", throwable.getCause() != null ? throwable.getCause().getMessage() : throwable.toString());
        return new IMessageFeignClient() {
            @Override
            public Integer findResponseMsgNums(int invitationId) {
                return -1;
            }
        };
    }
}
