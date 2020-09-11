package com.kanata.feign.hystrix.user;

import com.kanata.core.common.enums.ConcernType;
import com.kanata.feign.api.user.IUserInfoFeignClient;
import com.kanata.feign.dto.FansDto;
import com.kanata.feign.dto.Rest;
import com.kanata.feign.dto.UserInfoDto;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author chenyao
 * date 2020-09-04
 */
@Component
@Slf4j
public class IUserInfoFeignClientFallbackFactory implements FallbackFactory<IUserInfoFeignClient> {
    @Override
    public IUserInfoFeignClient create(Throwable throwable) {
        log.error("IUserInfoFeignClient处理超时：{}", throwable.getCause() != null ? throwable.getCause().getMessage() : throwable.toString());
        return new IUserInfoFeignClient() {
            @Override
            public FansDto findFans(int userId, int toUserid, ConcernType concernType) {
                FansDto fansDto = new FansDto();
                return fansDto;
            }

            @Override
            public UserInfoDto findUserInfo(int userId) {
                UserInfoDto userInfoDto = new UserInfoDto();
                userInfoDto.setId(-1);
                return userInfoDto;
            }
        };
    }

}
