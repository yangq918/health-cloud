package com.haici.health.component.autoconfigure.id;

import com.haici.health.component.autoconfigure.cache.RedisCacheClientConfiguration;
import com.haici.health.component.autoconfigure.lock.LockCondition;
import com.haici.health.component.cache.redis.RedisCacheClient;
import com.haici.health.component.id.IDGenerator;
import com.haici.health.component.id.redis.RedisCacheIDGenerator;
import com.haici.health.component.lock.DistributedLockTemplate;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

/**
 * @Auther: QiaoYang
 * @Date: 2020/3/17 20:47
 * @Description:
 */
@Configuration(proxyBeanMethods = false)
@AutoConfigureAfter(RedisCacheClientConfiguration.class)
@ConditionalOnBean(RedisCacheClient.class)
@ConditionalOnMissingBean(IDGenerator.class)
@Conditional(IDCondition.class)
public class RedisCacheIDGenConfiguration {
    @Bean
    @ConditionalOnMissingBean(name = "idGenerator")
    public IDGenerator idGenerator(IDProperties idProperties, RedisCacheClient cacheClient) {

        RedisCacheIDGenerator redisCacheIDGenerator = new RedisCacheIDGenerator(cacheClient);
        return redisCacheIDGenerator;
    }
}
