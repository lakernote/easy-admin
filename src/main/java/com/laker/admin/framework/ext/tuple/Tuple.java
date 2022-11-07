package com.laker.admin.framework.ext.tuple;

/**
 * @author laker
 */
public class Tuple {
    private Tuple() {
        //
    }

    /**
     * Construct a tuple of degree 2.
     */
    public static <T1, T2> Tuple2<T1, T2> of(T1 v1, T2 v2) {
        return new Tuple2<>(v1, v2);
    }

    /**
     * Construct a tuple of degree 3.
     */
    public static <T1, T2, T3> Tuple3<T1, T2, T3> of(T1 v1, T2 v2, T3 v3) {
        return new Tuple3<>(v1, v2, v3);
    }
}