package com.kanata.feign.hystrix.invitation;

import com.kanata.feign.api.invitation.IInvatitionFeignClient;
import com.kanata.feign.dto.InvitationDto;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author chenyao
 * date 2020-09-08
 */
@Slf4j
@Component
public class IInvitationFeignClientFallbackFactory implements FallbackFactory<IInvatitionFeignClient> {

    @Override
    public IInvatitionFeignClient create(Throwable throwable) {
        log.error("IInvatitionFeignClient处理超时：{}", throwable.getCause() != null ? throwable.getCause().getMessage() : throwable.toString());
        return new IInvatitionFeignClient() {
            @Override
            public InvitationDto findInvitationById(int userId, int invitationId) {
                InvitationDto invitationDto = new InvitationDto();
                invitationDto.setId(-1);
                return invitationDto;
            }
        };
    }
}
