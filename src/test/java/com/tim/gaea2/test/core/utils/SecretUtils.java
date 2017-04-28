package com.tim.gaea2.test.core.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.constraints.AssertTrue;

/**
 * Created by tianzhonghai on 2017/4/28.
 */
@SpringBootTest(classes = SecretUtils.class)
@RunWith(SpringRunner.class)
public class SecretUtils {

    @Test
    public void MD5Test(){

        String val = com.tim.gaea2.core.utils.SecretUtils.MD5("111111");


    }
}
