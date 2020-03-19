package com.haici.health.component.lock.redis;

import com.haici.health.component.cache.redis.RedisCacheClient;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * @Auther: QiaoYang
 * @Date: 2020/3/14 16:23
 * @Description:
 */
public class RedisLockInternals {

    private RedisCacheClient cacheClient;

    /**
     * 重试时间
     */
    private long retryAwait;

    private long lockTimeout;

    public RedisLockInternals(RedisCacheClient cacheClient, long retryAwait, long lockTimeout) {
        this.cacheClient = cacheClient;
        this.retryAwait = retryAwait;
        this.lockTimeout = lockTimeout;
    }


    String tryCacheLock(String lockId, long time, TimeUnit unit) {
        final long startMillis = System.currentTimeMillis();
        final Long millisToWait = (unit != null) ? unit.toMillis(time) : null;
        String lockValue = null;
        while (lockValue == null) {
            lockValue = createCacheKey(lockId);
            if (lockValue != null) {
                break;
            }
            if (System.currentTimeMillis() - startMillis - retryAwait > millisToWait) {
                break;
            }
            LockSupport.parkNanos(TimeUnit.MILLISECONDS.toNanos(retryAwait));
        }
        return lockValue;
    }

    private String createCacheKey(String lockId) {
        String value = lockId;
        String luaScript = "" + "\nlocal r = tonumber(redis.call('SETNX', KEYS[1],ARGV[1]));"
                + "\nredis.call('PEXPIRE',KEYS[1],ARGV[2]);" + "\nreturn r";
        List<String> keys = new ArrayList<String>();
        keys.add(lockId);
        List<String> args = new ArrayList<String>();
        args.add(value);
        args.add(lockTimeout + "");
        Long ret = cacheClient.eval(luaScript, keys, args,Long.class);
        if (new Long(1).equals(ret)) {
            return value;
        }
        return null;
    }

    void unlockCacheLock(String key, String value) {
        String luaScript = "" + "\nlocal v = redis.call('GET', KEYS[1]);" + "\nlocal r= 0;" + "\nif v == ARGV[1] then"
                + "\nr =redis.call('DEL',KEYS[1]);" + "\nend" + "\nreturn r";
        List<String> keys = new ArrayList<String>();
        keys.add(key);
        List<String> args = new ArrayList<String>();
        args.add(value);
        cacheClient.eval(luaScript, keys, args,Long.class);
    }
}
