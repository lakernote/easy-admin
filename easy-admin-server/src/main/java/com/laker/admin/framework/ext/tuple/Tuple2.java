package com.laker.admin.framework.ext.tuple;

/**
 * @author laker
 */
public class Tuple2<T1, T2> {
    private final T1 v1;
    private final T2 v2;

    public Tuple2(T1 v1, T2 v2) {
        this.v1 = v1;
        this.v2 = v2;
    }

    public T1 getV1() {
        return v1;
    }

    public T2 getV2() {
        return v2;
    }
}