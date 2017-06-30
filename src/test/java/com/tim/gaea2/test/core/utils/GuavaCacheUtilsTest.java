package com.tim.gaea2.test.core.utils;

import com.google.common.cache.Cache;
import com.tim.gaea2.core.cache.ICaching;
import com.tim.gaea2.core.utils.*;
import junit.framework.Assert;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by tianzhonghai on 2017/5/3.
 */
@SpringBootTest(classes = com.tim.gaea2.core.utils.GuavaCacheUtils.class)
@RunWith(SpringRunner.class)
@EnableAutoConfiguration
public class GuavaCacheUtilsTest {

    @Test
    public void test() throws Exception{
        ICaching caching = (ICaching)SpringUtil.getBean("defaultCache");

        String val = caching.get("test");

        org.junit.Assert.assertNull(val);

        Cache<String,Object> cache = GuavaCacheUtils.getCache();

        Object obj = cache.get("test",() -> {
            return "val2";
        });

        Object obj2 = cache.getIfPresent("test");

        org.junit.Assert.assertEquals(obj,obj2);
    }
}
