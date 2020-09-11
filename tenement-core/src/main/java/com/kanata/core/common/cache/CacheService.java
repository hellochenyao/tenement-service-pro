package com.kanata.core.common.cache;

/**
 * Created by ZHe on 2019/10/23.
 */
public interface CacheService {

    String get(CacheKey cacheKey);

    void set(CacheKey cacheKey, String cacheValue, Long second);

    void set(CacheKey cacheKey, Object cacheValue, Long second);

    <T> T get(CacheKey cacheKey, Class<T> clazz);

    long getExpireTime(CacheKey cacheKey);

    void remove(CacheKey cacheKey);
}
