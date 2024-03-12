package com.laker.admin.framework.cache;

public interface ICache {


    void put(String key, Object value);

    /**
     * @param key
     * @param value
     * @param timeout 单位：秒 s
     */
    void put(String key, Object value, long timeout);

    void remove(String key);

    <T> T get(String key);
}
