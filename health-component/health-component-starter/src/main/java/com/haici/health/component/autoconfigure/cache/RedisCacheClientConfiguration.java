package com.haici.health.component.autoconfigure.cache;

import com.haici.health.component.cache.CacheClient;
import com.haici.health.component.cache.redis.RedisCacheClient;
import com.haici.health.component.cache.redis.StringObjectRedisTemplate;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;

/**
 * @Auther: QiaoYang
 * @Date: 2020/3/14 00:14
 * @Description:
 */
@Configuration(proxyBeanMethods = false)
@AutoConfigureAfter(RedisAutoConfiguration.class)
@ConditionalOnBean(RedisConnectionFactory.class)
@ConditionalOnMissingBean(CacheClient.class)
@Conditional(CacheClientCondition.class)
public class RedisCacheClientConfiguration {


    @Bean
    @ConditionalOnMissingBean(name = "stringObjectRedisTemplate")
    public StringObjectRedisTemplate stringObjectRedisTemplate(CacheClientProperties cacheClientProperties, RedisConnectionFactory redisConnectionFactory) {

        StringObjectRedisTemplate template = new StringObjectRedisTemplate();
        template.setConnectionFactory(redisConnectionFactory);
        if(null!=cacheClientProperties.getRedis().getSerializerType())
        {
            template.setValueSerializer(cacheClientProperties.getRedis().getSerializerType().getRedisSerializer());
            template.setHashValueSerializer(cacheClientProperties.getRedis().getSerializerType().getRedisSerializer());
        }
        return template;
    }

    @Bean
    @ConditionalOnMissingBean(name = "cacheClient")
    RedisCacheClient cacheClient(CacheClientProperties cacheClientProperties, StringObjectRedisTemplate stringObjectRedisTemplate) {

        return RedisCacheClient.builder(stringObjectRedisTemplate)
                .withCacheTime(cacheClientProperties.getRedis().getCacheTime())
                .withPrefix(cacheClientProperties.getRedis().getPrefix())
                .build();
    }
}
