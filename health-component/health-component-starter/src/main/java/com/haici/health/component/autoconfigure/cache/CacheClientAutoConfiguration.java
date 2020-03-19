package com.haici.health.component.autoconfigure.cache;

import com.haici.health.component.autoconfigure.common.AbstractConfigurationImportSelector;
import com.haici.health.component.cache.CacheClient;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @Auther: QiaoYang
 * @Date: 2020/3/14 10:15
 * @Description:
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(CacheClient.class)
@ConditionalOnMissingBean(CacheClient.class)
@EnableConfigurationProperties(CacheClientProperties.class)
@AutoConfigureAfter({RedisAutoConfiguration.class})
@Import({CacheClientAutoConfiguration.CacheClientConfigurationImportSelector.class})
public class CacheClientAutoConfiguration {


    /**
     * {@link ImportSelector} to add {@link CacheClientType} configuration classes.
     */
    static class CacheClientConfigurationImportSelector extends AbstractConfigurationImportSelector<CacheClientType> {


    }
}
