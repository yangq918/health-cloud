package com.haici.health.component.autoconfigure.web;

import com.haici.health.compoent.web.interceptor.GlobalContextInterceptor;
import com.haici.health.compoent.web.interceptor.HcContextInterceptor;
import com.haici.health.component.id.helper.MessageIdHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Auther: QiaoYang
 * @Date: 2020/3/19 21:55
 * @Description:
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnBean(MessageIdHelper.class)
public class WebInterceptorAutoConfiguration {

    @Autowired
    private MessageIdHelper messageIdHelper;

    @Bean
    public HcContextInterceptor hcContextInterceptor() {
        HcContextInterceptor interceptor = new HcContextInterceptor();
        interceptor.setMessageIdHelper(messageIdHelper);
        return interceptor;
    }


    @Bean
    public GlobalContextInterceptor globalContextInterceptor() {
        GlobalContextInterceptor interceptor = new GlobalContextInterceptor();
        return interceptor;
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {

            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                registry.addInterceptor(hcContextInterceptor()).addPathPatterns("/api/**");
                registry.addInterceptor(globalContextInterceptor()).addPathPatterns("/**");
            }
        };
    }
}

