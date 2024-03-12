package com.laker.admin.framework.ext.sample;

import org.junit.Test;

/**
 * @date: 2022/11/17
 **/
public class SampleUtilsTest {
    @Test
    public void test() {
        for (int i = 0; i < 100; i++) {
            System.out.println(i + ":" + SampleUtils.sample(30));
        }
    }
}
