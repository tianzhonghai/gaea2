package com.tim.gaea2.core.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

/**
 * Created by tianzhonghai on 2017/6/30.
 */
public class GuavaCache implements ICaching {
    Cache<String, Object> cache;

    public GuavaCache(){
        cache = CacheBuilder.newBuilder().maximumSize(1000)
                .build(); // look Ma, no CacheLoader
    }

    public <T> T get(String key) {
        return (T)cache.getIfPresent(key);
    }

    public <T> T getOrAdd(String key, Callable<T> callble) throws ExecutionException {
        return (T)cache.get(key, callble);

    }

    public <T> void put(String key, T value) {
        cache.put(key,value);
    }

    public void remove(String key) {
        cache.invalidate(key);
    }
}
