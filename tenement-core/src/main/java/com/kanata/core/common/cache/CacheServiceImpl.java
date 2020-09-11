package com.kanata.core.common.cache;

import com.kanata.core.util.RedisClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by ZHe on 2019/10/23.
 */
@Component
public class CacheServiceImpl implements CacheService {

    @Autowired
    private RedisClient redisClient;

    @Override
    public String get(CacheKey cacheKey) {
        return redisClient.get(cacheKey.toString());
    }

    @Override
    public void set(CacheKey cacheKey, String cacheValue, Long second) {
        redisClient.set(cacheKey.toString(),cacheValue,second);
    }

    @Override
    public void set(CacheKey cacheKey, Object cacheValue, Long second) {
        redisClient.set(cacheKey.toString(),cacheValue,second);
    }

    @Override
    public <T> T get(CacheKey cacheKey, Class<T> clazz) {
        return redisClient.get(cacheKey.toString(),clazz);
    }

    @Override
    public long getExpireTime(CacheKey cacheKey) {
        return redisClient.getExpireTime(cacheKey.toString());
    }

    @Override
    public void remove(CacheKey cacheKey) {
        redisClient.remove(cacheKey.toString());
    }
}
