package com.haici.health.component.autoconfigure.lock;

import com.haici.health.component.autoconfigure.cache.CacheClientAutoConfiguration;
import com.haici.health.component.autoconfigure.cache.CacheClientConfigurations;
import com.haici.health.component.autoconfigure.cache.CacheClientType;
import com.haici.health.component.autoconfigure.common.AbstractConfigurationImportSelector;
import com.haici.health.component.autoconfigure.common.ConfigurationsUtils;
import com.haici.health.component.lock.DistributedLockTemplate;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @Auther: QiaoYang
 * @Date: 2020/3/14 17:37
 * @Description:
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(DistributedLockTemplate.class)
@ConditionalOnMissingBean(DistributedLockTemplate.class)
@EnableConfigurationProperties(LockProperties.class)
@AutoConfigureAfter({CacheClientAutoConfiguration.class})
@Import({LockAutoConfiguration.LockConfigurationImportSelector.class})
public class LockAutoConfiguration {
    static class LockConfigurationImportSelector extends AbstractConfigurationImportSelector<LockType> {

    }
}
