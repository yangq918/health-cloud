package com.haici.health.component.autoconfigure.id;

/**
 * @Auther: QiaoYang
 * @Date: 2020/3/17 23:26
 * @Description:
 */

import com.alibaba.cloud.nacos.registry.NacosServiceRegistryAutoConfiguration;
import com.haici.health.component.autoconfigure.cache.CacheClientAutoConfiguration;
import com.haici.health.component.autoconfigure.cache.CacheClientProperties;
import com.haici.health.component.autoconfigure.common.AbstractConfigurationImportSelector;
import com.haici.health.component.cache.CacheClient;
import com.haici.health.component.id.IDGenerator;
import com.haici.health.component.id.helper.MessageIdHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;


@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(IDGenerator.class)
@ConditionalOnMissingBean(IDGenerator.class)
@EnableConfigurationProperties(IDProperties.class)
@AutoConfigureAfter(NacosServiceRegistryAutoConfiguration.class)
@Import({IDAutoConfiguration.IDConfigurationImportSelector.class})
public class IDAutoConfiguration {

    @Value("${health.platformKey:}")
    private String platformKey;

    @Value(("${health.component.id.message-id-prefix:}"))
    private String messageIdPrefix;

    @Bean
    public MessageIdHelper messageIdHelper(IDGenerator idGenerator) {
        String prefix = StringUtils.isEmpty(messageIdPrefix) ? platformKey : messageIdPrefix;

        MessageIdHelper messageIdHelper = new MessageIdHelper();
        messageIdHelper.setIdGenerator(idGenerator);
        messageIdHelper.setPrefix(prefix);
        return messageIdHelper;
    }


    static class IDConfigurationImportSelector extends AbstractConfigurationImportSelector<IDGenType> {
    }
}
