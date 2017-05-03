//package com.tim.gaea2.test.core.utils;
//
//import com.tim.gaea2.core.utils.CacheUtils;
//import org.ehcache.Cache;
//import org.ehcache.CacheManager;
//import org.ehcache.config.builders.CacheConfigurationBuilder;
//import org.ehcache.config.builders.ResourcePoolsBuilder;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.util.Assert;
//
///**
// * Created by tianzhonghai on 2017/5/2.
// */
//@SpringBootTest(classes = SecretUtils.class)
//@RunWith(SpringRunner.class)
//public class CacheUtilsTest {
//
//    @Test
//    public void getDefaultManager(){
//        try(CacheManager manager = CacheUtils.getManager("default",String.class,String.class)) {
//            org.junit.Assert.assertNotNull(manager);
//
//            Cache<String, String> cache = manager.getCache("default", String.class, String.class);
//            if (cache == null) {
//                cache = manager.createCache("default",
//                        CacheConfigurationBuilder.newCacheConfigurationBuilder(String.class, String.class, ResourcePoolsBuilder.heap((10)))
//                                .build()
//                );
//            }
//            cache = manager.getCache("default", String.class, String.class);
//            org.junit.Assert.assertNotNull(cache);
//
//            cache.put("myname", "tim.tian");
//            String name = cache.get("myname");
//
//            org.junit.Assert.assertEquals(name, "tim.tian");
//
//            cache = manager.getCache("default", String.class, String.class);
//            name = cache.get("myname");
//
//            manager.removeCache("test");
//            cache = manager.getCache("test", String.class, String.class);
//            org.junit.Assert.assertNull(cache);
//
//            //manager.close();
//        }
//    }
//
//}
