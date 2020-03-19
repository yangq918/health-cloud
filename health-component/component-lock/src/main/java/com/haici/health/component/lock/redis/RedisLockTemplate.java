package com.haici.health.component.lock.redis;

import com.haici.health.component.cache.redis.RedisCacheClient;
import com.haici.health.component.lock.DistributedLockTemplate;
import com.haici.health.component.lock.LockCallback;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @Auther: QiaoYang
 * @Date: 2020/3/14 17:14
 * @Description:
 */
@Slf4j
public class RedisLockTemplate implements DistributedLockTemplate {

    private String prefix;

    private RedisCacheClient cacheClient;

    private long retryAwait = 300L;

    private long lockTimeout = 2000L;

    @Override
    public <T> T execute(String lockId, int timeout, LockCallback<T> callback) {
        RedisReentrantLock lock = null;
        boolean getLock = false;
        try {
            lock = new RedisReentrantLock(prefix, lockId, retryAwait, lockTimeout, cacheClient);
            if (lock.tryLock(new Long(timeout), TimeUnit.MILLISECONDS)) {
                getLock = true;
                return callback.onGetLock();
            } else {
                return callback.onTimeout();
            }
        } catch (InterruptedException ex) {
            log.error(ex.getMessage(), ex);
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            if (getLock) {
                lock.unlock();
            }
        }
        return null;
    }


    public static  RedisLockTemplateBuilder builder(RedisCacheClient cacheClient)
    {
        return new RedisLockTemplateBuilder(cacheClient);
    }


    public static class RedisLockTemplateBuilder{
        private String prefix;

        private RedisCacheClient cacheClient;

        private long retryAwait = 300L;

        private long lockTimeout = 2000L;

        public RedisLockTemplateBuilder(RedisCacheClient cacheClient) {
            this.cacheClient = cacheClient;
        }

        public RedisLockTemplateBuilder withPrefix(String prefix)
        {
            this.prefix = prefix;
            return this;
        }

        public RedisLockTemplateBuilder withRetryAwait(long retryAwait)
        {
            this.retryAwait = retryAwait;
            return this;
        }

        public RedisLockTemplateBuilder withLockTimeout(long lockTimeout)
        {
            this.lockTimeout = lockTimeout;
            return this;
        }

        public RedisLockTemplate build()
        {
            RedisLockTemplate template= new RedisLockTemplate();
            template.cacheClient = cacheClient;
            template.prefix = prefix;
            template.lockTimeout = lockTimeout;
            template.retryAwait = retryAwait;

            return template;
        }
    }



}
