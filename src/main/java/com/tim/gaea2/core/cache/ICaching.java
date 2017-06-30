package com.tim.gaea2.core.cache;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

/**
 * Created by tianzhonghai on 2017/6/30.
 */
public interface ICaching {
    <T> T get(String key);

    <T> T getOrAdd(String key, Callable<T> callble) throws ExecutionException;

    <T> void put(String key, T value);

    void remove(String key);


}
