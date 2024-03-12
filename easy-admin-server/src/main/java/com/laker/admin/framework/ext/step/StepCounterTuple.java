package com.laker.admin.framework.ext.step;

import java.util.Objects;
import java.util.concurrent.atomic.LongAdder;

/**
 * <pre>
 *      用于内存 聚合指标，
 *      例如 视频的点赞数，观看数，按照定时步长统计 例如 60m
 *      id：videoid 001，count1:12，count2:12
 *      id：videoid 002，count1:9，count2:20
 * </pre>
 *
 * @author laker
 */
public class StepCounterTuple {
    private String id;
    private final LongAdder count1 = new LongAdder();
    /**
     * 可以 自己扩展 count n ，double n
     */
    private final LongAdder count2 = new LongAdder();

    public StepCounterTuple(String id) {
        this.id = id;
    }


    public void increment1(long amount) {
        count1.add(amount);
    }

    /**
     * 调用一次就会 获取sum 然后置位 0
     *
     * @return
     */
    public long count1() {
        return count1.sumThenReset();
    }


    public void increment2(long amount) {
        count2.add(amount);
    }

    public long count2() {
        return count2.sumThenReset();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        StepCounterTuple that = (StepCounterTuple) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public String getId() {
        return id;
    }
}
