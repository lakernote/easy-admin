package com.laker.admin.framework.ext.sample;

import java.util.Collection;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

/**
 * @author: laker
 * @date: 2022/11/17
 **/
public class SampleUtils {

    /**
     * 根据用户输入的采样率返回是否可以采样
     *
     * @param samplePercentage 采样率int类型：35 即35%采样率
     * @return 是否可以采样
     */
    public static boolean sample(int samplePercentage) {
        // ThreadLocalRandom.current().nextInt()
        // 返回介于零（含）和指定界限（不含）之间的伪随机int值。
        return ThreadLocalRandom.current().nextInt(100) < samplePercentage;
    }

    /**
     * 根据用户输入的采样率返回是否采样
     *
     * @param samplePercentage 采样率int类型：35 即35%采样率
     * @return 是否可以采样
     */
    public static <E> Collection<E> sample(Collection<E> collection, int samplePercentage) {
        return collection.stream().filter(o -> sample(samplePercentage)).collect(Collectors.toList());
    }

}

