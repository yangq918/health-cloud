package com.haici.health.component.autoconfigure.cache;

import com.haici.health.component.autoconfigure.common.AbstractTypeCondition;
import org.springframework.boot.autoconfigure.condition.ConditionMessage;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.boot.context.properties.bind.BindException;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.ClassMetadata;

/**
 * @Auther: QiaoYang
 * @Date: 2020/3/14 00:27
 * @Description:
 */
public class CacheClientCondition extends AbstractTypeCondition<CacheClientType> {
    @Override
    public String typeName() {
        return "Cache";
    }

    @Override
    public String specifiedProperties() {
        return "health.component.cache-client.type";
    }

}
