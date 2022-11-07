package com.laker.admin.framework.ext.tuple;

/**
 * @author laker
 */
public class Tuple3<T1, T2, T3> extends Tuple2<T1, T2> {
    private final T3 v3;

    public Tuple3(T1 v1, T2 v2, T3 v3) {
        super(v1, v2);
        this.v3 = v3;
    }

    public T3 getV3() {
        return v3;
    }
}