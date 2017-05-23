package com.tim.gaea2.core.utils;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * Created by tianzhonghai on 2017/5/3.
 */
public class GuavaCacheUtils {

    private static Cache<String,Object> cache;

    static {
        cache = CacheBuilder.newBuilder().maximumSize(10000).expireAfterWrite(3600, TimeUnit.SECONDS).build();
    }

    public static Cache<String,Object> getCache() {
        return cache;
    }


    // return Optional.ofNullable((Map<String,V>) productCacheContainer.getAllPresent(cacheKeyList)).orElse(null);

}
