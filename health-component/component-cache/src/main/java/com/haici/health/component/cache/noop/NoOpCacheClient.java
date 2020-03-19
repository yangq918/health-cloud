package com.haici.health.component.cache.noop;

import com.haici.health.component.cache.CacheClient;

import java.util.List;

/**
 * @Auther: QiaoYang
 * @Date: 2020/3/14 09:58
 * @Description:
 */
public class NoOpCacheClient implements CacheClient {
    @Override
    public <T> T get(String key) {
        return null;
    }

    @Override
    public <T> T getNoPrefix(String key) {
        return null;
    }

    @Override
    public <T> boolean set(String key, T o) {
        return true;
    }

    @Override
    public <T> boolean set(String key, T o, int timeout) {
        return true;
    }

    @Override
    public boolean delete(String key) {
        return true;
    }

    @Override
    public long incr(String key) {
        throw new UnsupportedOperationException("不支持该操作");
    }

    @Override
    public long incr(String key, int expire) {
        throw new UnsupportedOperationException("不支持该操作");
    }

    @Override
    public Long getIncrValue(String key) {
        throw new UnsupportedOperationException("不支持该操作");
    }

    @Override
    public void setIncrValue(String key, long value, int expire) {

    }

    @Override
    public <T> T eval(String script, List<String> keys, List<String> args, Class<T> clz) {
        return null;
    }


    @Override
    public Boolean exists(String key) {
        return false;
    }

    @Override
    public void setExpire(String key, int expire) {

    }

    @Override
    public Object getCacheObject() {
        return null;
    }
}
