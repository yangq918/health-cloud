package com.haici.health.component.autoconfigure.web;

import com.haici.health.compoent.web.session.HealthWebSessionIdResolver;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.session.SessionAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Auther: QiaoYang
 * @Date: 2020/3/22 14:17
 * @Description:
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(HealthWebSessionIdResolver.class)
@AutoConfigureBefore(SessionAutoConfiguration.class)
@EnableConfigurationProperties(HealthWebSessionProperties.class)
public class WebSessionAutoConfiguration {

    @Bean
    public HealthWebSessionIdResolver healthWebSessionIdResolver(HealthWebSessionProperties healthWebSessionProperties) {
        return new HealthWebSessionIdResolver(healthWebSessionProperties);
    }
}
