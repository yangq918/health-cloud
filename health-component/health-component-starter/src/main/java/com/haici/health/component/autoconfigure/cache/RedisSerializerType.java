package com.haici.health.component.autoconfigure.cache;

import com.haici.health.component.cache.redis.serializer.FastSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * @Auther: QiaoYang
 * @Date: 2020/3/14 12:10
 * @Description:
 */
public enum RedisSerializerType {
    JDK(RedisSerializer.java()),
    JSON(RedisSerializer.json()),
    FST(new FastSerializationRedisSerializer());


    private RedisSerializer<Object> redisSerializer;

    RedisSerializerType(RedisSerializer<Object> redisSerializer) {
        this.redisSerializer = redisSerializer;
    }


    public RedisSerializer<Object> getRedisSerializer() {
        return redisSerializer;
    }
}
