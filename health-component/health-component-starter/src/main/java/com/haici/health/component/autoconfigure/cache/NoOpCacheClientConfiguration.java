package com.haici.health.component.autoconfigure.cache;

import com.haici.health.component.cache.CacheClient;
import com.haici.health.component.cache.noop.NoOpCacheClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

/**
 * @Auther: QiaoYang
 * @Date: 2020/3/14 00:26
 * @Description:
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnMissingBean(CacheClient.class)
@Conditional(CacheClientCondition.class)
public class NoOpCacheClientConfiguration {

    @Bean
    public NoOpCacheClient cacheClient()
    {
        return new NoOpCacheClient();
    }
}
