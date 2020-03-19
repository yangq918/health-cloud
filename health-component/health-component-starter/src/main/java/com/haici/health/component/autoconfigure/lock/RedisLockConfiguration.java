package com.haici.health.component.autoconfigure.lock;

import com.haici.health.component.autoconfigure.cache.RedisCacheClientConfiguration;
import com.haici.health.component.cache.redis.RedisCacheClient;
import com.haici.health.component.lock.DistributedLockTemplate;
import com.haici.health.component.lock.redis.RedisLockTemplate;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

/**
 * @Auther: QiaoYang
 * @Date: 2020/3/14 20:09
 * @Description:
 */
@Configuration(proxyBeanMethods = false)
@AutoConfigureAfter(RedisCacheClientConfiguration.class)
@ConditionalOnBean(RedisCacheClient.class)
@ConditionalOnMissingBean(DistributedLockTemplate.class)
@Conditional(LockCondition.class)
public class RedisLockConfiguration {

    @Bean
    @ConditionalOnMissingBean(name = "distributedLockTemplate")
    public RedisLockTemplate distributedLockTemplate(RedisCacheClient cacheClient, LockProperties lockProperties) {
        RedisLockTemplate.RedisLockTemplateBuilder builder = RedisLockTemplate.builder(cacheClient);
        if (null != lockProperties && null != lockProperties.getRedis()) {
            LockProperties.Redis redis = lockProperties.getRedis();
            builder.withPrefix(redis.getPrefix()).withLockTimeout(redis.getLockTimeout())
                    .withRetryAwait(redis.getRetryAwait());
        }
        return builder.build();
    }
}
