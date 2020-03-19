package com.haici.health.component.lock.redis;

import com.haici.health.component.cache.redis.RedisCacheClient;
import com.haici.health.component.lock.DistributedReentrantLock;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Auther: QiaoYang
 * @Date: 2020/3/14 16:21
 * @Description:
 */
public class RedisReentrantLock implements DistributedReentrantLock {


    private final ConcurrentMap<Thread, LockData> threadData = new ConcurrentHashMap<>();

    private RedisLockInternals internals;

    private String lockId;


    public RedisReentrantLock(String prefix, String lockId, long retryAwait, long lockTimeout, RedisCacheClient cacheClient) {
        this.lockId = prefix + "_" + lockId;
        this.internals = new RedisLockInternals(cacheClient, retryAwait, lockTimeout);
    }

    @Override
    public boolean tryLock(long timeout, TimeUnit unit) throws InterruptedException {
        Thread currentThread = Thread.currentThread();
        LockData lockData = threadData.get(currentThread);
        if (lockData != null) {
            lockData.lockCount.incrementAndGet();
            return true;
        }
        String lockVal = internals.tryCacheLock(lockId, timeout, unit);
        if (lockVal != null) {
            LockData newLockData = new LockData(currentThread, lockVal);
            threadData.put(currentThread, newLockData);
            return true;
        }
        return false;
    }

    @Override
    public void unlock() {
        Thread currentThread = Thread.currentThread();
        LockData lockData = threadData.get(currentThread);
        if (lockData == null) {
            throw new IllegalMonitorStateException("You do not own the lock: " + lockId);
        }
        int newLockCount = lockData.lockCount.decrementAndGet();
        if (newLockCount > 0) {
            return;
        }
        if (newLockCount < 0) {
            throw new IllegalMonitorStateException("Lock count has gone negative for lock: " + lockId);
        }
        try {
            internals.unlockCacheLock(lockId, lockData.lockVal);
        } finally {
            threadData.remove(currentThread);
        }
    }


    private static class LockData {
        final String lockVal;
        final AtomicInteger lockCount = new AtomicInteger(1);

        private LockData(Thread owningThread, String lockVal) {
            this.lockVal = lockVal;
        }
    }
}
