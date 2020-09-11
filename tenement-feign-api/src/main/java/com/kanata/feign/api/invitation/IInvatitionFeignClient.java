package com.kanata.feign.api.invitation;

import com.kanata.feign.constant.FeignConstant;
import com.kanata.feign.dto.InvitationDto;
import com.kanata.feign.hystrix.invitation.IInvitationFeignClientFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author chenyao
 * date 2020-09-08
 */
@FeignClient(name = FeignConstant.INVITATION_APPLICATION_NAME,
        fallbackFactory = IInvitationFeignClientFallbackFactory.class)
public interface IInvatitionFeignClient {

    @GetMapping(value = "/app/operation/{userId}/{invitationId}")
    InvitationDto findInvitationById(@RequestParam("userId") int userId,@RequestParam("invitationId") int invitationId);

}
