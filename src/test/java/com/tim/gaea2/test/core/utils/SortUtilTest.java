package com.tim.gaea2.test.core.utils;

import com.tim.gaea2.core.utils.SortUtil;
import org.assertj.core.api.ArraySortedAssert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by tianzhonghai on 2017/6/12.
 */
@SpringBootTest(classes = SecretUtils.class)
@RunWith(SpringRunner.class)
public class SortUtilTest {
    @Test
    public void InsertSort()
    {
        int[] a = {32,43,23,13,5};
        SortUtil.InsertSort(a);

    }
}
