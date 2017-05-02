package com.tim.gaea2.core.utils;

import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by tianzhonghai on 2017/5/2.
 */
public class CacheUtils
{
    public static CacheManager getManager(String alias){
        CacheManager cacheManager = CacheManagerBuilder.newCacheManagerBuilder()
                .withCache(alias,
                        CacheConfigurationBuilder.newCacheConfigurationBuilder(String.class, String.class,
                                ResourcePoolsBuilder.heap(100))
                                .build())
                .build(true);
        return cacheManager;
    }

    public static CacheManager getDefaultManager(){
        return getManager("default");
    }
}