package com.haici.health.component.autoconfigure.lock;

import com.haici.health.component.autoconfigure.cache.CacheClientConfigurations;
import com.haici.health.component.autoconfigure.cache.CacheClientType;
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
 * @Date: 2020/3/14 20:13
 * @Description:
 */
public class LockCondition extends AbstractTypeCondition<LockType> {
    @Override
    public String typeName() {
        return "Lock";
    }

    @Override
    public String specifiedProperties() {
        return "health.component.lock.type";
    }

}
