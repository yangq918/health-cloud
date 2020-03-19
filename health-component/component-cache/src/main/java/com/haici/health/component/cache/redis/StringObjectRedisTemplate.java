package com.haici.health.component.cache.redis;

import com.haici.health.component.cache.redis.serializer.FastSerializationRedisSerializer;
import org.springframework.data.redis.connection.DefaultStringRedisConnection;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * @Auther: QiaoYang
 * @Date: 2020/3/13 23:14
 * @Description:
 */
public class StringObjectRedisTemplate extends RedisTemplate<String,Object> {

    /**
     * 初始化
     */
    public StringObjectRedisTemplate() {
        setKeySerializer(RedisSerializer.string());
        setValueSerializer(new FastSerializationRedisSerializer());
        setHashKeySerializer(RedisSerializer.string());
        setHashValueSerializer(new FastSerializationRedisSerializer());
    }

    /**
     * 初始化
     * @param connectionFactory
     */
    public StringObjectRedisTemplate(RedisConnectionFactory connectionFactory) {
        this();
        setConnectionFactory(connectionFactory);
        afterPropertiesSet();
    }
}
