package com.laker.admin.framework.ext.tuple;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author: laker
 * @date: 2022/11/7
 **/
public class TupleTest {

    @Test
    public void test() {
        Tuple2<Integer, String> tuple2 = Tuple.of(1, "abc");
        Assert.assertEquals(1, tuple2.getV1().intValue());
        Assert.assertEquals("abc", tuple2.getV2());
    }
}
