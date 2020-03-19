package com.haici.health.component.autoconfigure.web;

import com.haici.health.compoent.web.aop.WebLoggerMethodInterceptor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Auther: QiaoYang
 * @Date: 2020/3/19 18:00
 * @Description:
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(WebLoggerMethodInterceptor.class)
public class WebLogAutoConfiguration {
    public static final String webExecution = "@within(org.springframework.web.bind.annotation.RestController) || @within(org.springframework.stereotype.Controller)";


    @Bean
    public DefaultPointcutAdvisor webLoggerPointcutAdvisor() {
        WebLoggerMethodInterceptor interceptor = new WebLoggerMethodInterceptor();
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(webExecution);
        // 配置增强类advisor
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor();
        advisor.setPointcut(pointcut);
        advisor.setAdvice(interceptor);
        advisor.setOrder(0);
        return advisor;
    }
}
