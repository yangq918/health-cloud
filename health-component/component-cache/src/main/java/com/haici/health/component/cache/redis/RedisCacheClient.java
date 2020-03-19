package com.haici.health.component.cache.redis;

import com.haici.health.component.cache.CacheClient;
import com.haici.health.component.cache.redis.serializer.FastSerializationRedisSerializer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @Auther: QiaoYang
 * @Date: 2020/3/10 19:17
 * @Description:
 */
@Slf4j
public class RedisCacheClient implements CacheClient {


    private String prefix;

    /**
     * 默认过期时间
     */
    private int cacheTime = 60 * 60 * 1;

    /**
     * 构造函数
     */
    public RedisCacheClient() {
    }

    /**
     * 构造函数
     *
     * @param prefix
     * @param cacheTime
     * @param stringObjectRedisTemplate
     */
    public RedisCacheClient(String prefix, int cacheTime, StringObjectRedisTemplate stringObjectRedisTemplate) {
        this.prefix = prefix;
        this.cacheTime = cacheTime;
        this.stringObjectRedisTemplate = stringObjectRedisTemplate;
    }

    private StringObjectRedisTemplate stringObjectRedisTemplate;

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public void setStringObjectRedisTemplate(StringObjectRedisTemplate stringObjectRedisTemplate) {
        this.stringObjectRedisTemplate = stringObjectRedisTemplate;
    }

    public void setCacheTime(int cacheTime) {
        this.cacheTime = cacheTime;
    }

    @Override
    public <T> T get(String key) {
        return (T) stringObjectRedisTemplate.opsForValue().get(prefix + key);
    }

    @Override
    public <T> T getNoPrefix(String key) {
        return (T) stringObjectRedisTemplate.opsForValue().get(key);
    }

    @Override
    public <T> boolean set(String key, T o) {
        return set(key, o, cacheTime);
    }

    @Override
    public <T> boolean set(String key, T o, int timeout) {
        try {
            stringObjectRedisTemplate.opsForValue().set(prefix + key, o, (long) cacheTime, TimeUnit.SECONDS);
            return true;
        } catch (Exception e) {
            log.error("set to redis has error,key:{}", key, e);
            return false;
        }
    }

    @Override
    public boolean delete(String key) {

        try {
            stringObjectRedisTemplate.opsForValue().getOperations().delete(prefix + key);
            return true;
        } catch (Exception e) {
            log.error("delete key from redis has error,key:{}", key, e);
            return false;
        }
    }

    @Override
    public long incr(String key) {
        return incr(key, -1);
    }

    @Override
    public long incr(String key, int expire) {

        long value = stringObjectRedisTemplate.opsForValue().increment(prefix + key);
        switch (expire) {
            case -1:
                stringObjectRedisTemplate.persist(prefix + key);
                break;
            case 0:
                break;
            default:
                stringObjectRedisTemplate.expire(prefix + key, (long) expire, TimeUnit.SECONDS);
                break;
        }

        return value;
    }

    @Override
    public Long getIncrValue(String key) {
        Object o = stringObjectRedisTemplate.opsForValue().get(prefix + key);
        String value = Objects.toString(0, null);
        return value == null ? null : Long.valueOf(value);
    }

    @Override
    public void setIncrValue(String key, long value, int expire) {
        stringObjectRedisTemplate.opsForValue().increment(prefix + key, value);
        switch (expire) {
            case -1:
                stringObjectRedisTemplate.persist(prefix + key);
                break;
            case 0:
                break;
            default:
                stringObjectRedisTemplate.expire(prefix + key, (long) expire, TimeUnit.SECONDS);
                break;
        }

    }

    @Override
    public <T> T eval(String script, List<String> keys, List<String> args,Class<T> clz) {

        DefaultRedisScript<T> redisScript = new DefaultRedisScript<>();
        redisScript.setScriptText(script);
        redisScript.setResultType(clz);
        String[] argArrays = null == args ? null : args.toArray(new String[args.size()]);
        T result =  stringObjectRedisTemplate.execute(redisScript, RedisSerializer.string(), new FastSerializationRedisSerializer<T>(), keys, argArrays);
        return result;
    }

    @Override
    public Boolean exists(String key) {
        return stringObjectRedisTemplate.hasKey(prefix + key);
    }

    @Override
    public void setExpire(String key, int expire) {
        stringObjectRedisTemplate.expire(key, (long) expire, TimeUnit.SECONDS);
    }

    @Override
    public Object getCacheObject() {
        return stringObjectRedisTemplate;
    }


    public static RedisCacheClientBuilder builder(StringObjectRedisTemplate stringObjectRedisTemplate) {
        return new RedisCacheClientBuilder(stringObjectRedisTemplate);
    }

    public static class RedisCacheClientBuilder {

        private String prefix;

        /**
         * 默认过期时间
         */
        private int cacheTime = 60 * 60 * 1;

        private StringObjectRedisTemplate stringObjectRedisTemplate;

        public RedisCacheClientBuilder(StringObjectRedisTemplate stringObjectRedisTemplate) {
            this.stringObjectRedisTemplate = stringObjectRedisTemplate;
        }

        public RedisCacheClient build() {
            RedisCacheClient cacheClient = new RedisCacheClient(prefix, cacheTime, stringObjectRedisTemplate);

            return cacheClient;
        }

        public RedisCacheClientBuilder withPrefix(String prefix) {
            this.prefix = prefix;
            return this;
        }


        public RedisCacheClientBuilder withCacheTime(int cacheTime) {
            this.cacheTime = cacheTime;
            return this;
        }
    }
}
