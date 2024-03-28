package com.laker.admin.framework.cache;

import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.LFUCache;

/**
 * @author laker
 */
public class EasyMapCache implements IEasyCache {
    LFUCache<String, Object> CACHE = CacheUtil.newLFUCache(1000);

    @Override
    public void put(String key, Object value) {
        CACHE.put(key, value);
    }

    @Override
    public void put(String key, Object value, long timeout) {
        CACHE.put(key, value, timeout * 1000);
    }

    @Override
    public void remove(String key) {
        CACHE.remove(key);
    }

    @Override
    public <T> T get(String key) {
        return (T) CACHE.get(key);
    }
}
