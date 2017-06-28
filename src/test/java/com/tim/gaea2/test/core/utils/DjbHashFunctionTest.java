package com.tim.gaea2.test.core.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by tianzhonghai on 2017/6/28.
 */
@SpringBootTest(classes = SecretUtils.class)
@RunWith(SpringRunner.class)
public class DjbHashFunctionTest {
    @Test
    public void hashTest(){

        int shardingVal = com.tim.gaea2.core.utils.DjbHashFunction.hash("asdfasdfsadf");


    }
}
