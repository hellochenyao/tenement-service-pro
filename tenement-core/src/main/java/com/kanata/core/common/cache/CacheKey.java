package com.kanata.core.common.cache;

import lombok.AllArgsConstructor;

/**
 * Created by ZHe on 2019/10/23.
 */
@AllArgsConstructor
public class CacheKey {

    private CacheNamespaceType cacheNamespaceType;

    private String cacheId;

    @Override
    public String toString() {
        return String.format("%s_%s",cacheNamespaceType.name(),cacheId);
    }
}
