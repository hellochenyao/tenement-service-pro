package com.kanata.feign.hystrix.user;

import com.kanata.feign.api.user.IUserRelationFeignClient;
import com.kanata.feign.dto.UserRelationDto;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author chenyao
 * date 2020-09-09
 */
@Slf4j
@Component
public class IUserRelationFeignClientFallbackFactory implements FallbackFactory<IUserRelationFeignClient> {
    @Override
    public IUserRelationFeignClient create(Throwable throwable) {
        log.error("IUserRelationFeignClient处理超时：{}", throwable.getCause() != null ? throwable.getCause().getMessage() : throwable.toString());
        return new IUserRelationFeignClient() {
            @Override
            public UserRelationDto findRelation(int userId, int friendId) {
                UserRelationDto userRelationDto = new UserRelationDto();
                userRelationDto.setId(-1);
                return userRelationDto;
            }
        };
    }
}
