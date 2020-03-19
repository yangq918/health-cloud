package com.haici.health.component.autoconfigure.common;

import com.haici.health.component.autoconfigure.lock.LockConfigurations;
import com.haici.health.component.autoconfigure.lock.LockType;
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

import java.lang.reflect.ParameterizedType;

/**
 * @Auther: QiaoYang
 * @Date: 2020/3/17 21:50
 * @Description:
 */
public abstract class AbstractTypeCondition<T extends Enum> extends SpringBootCondition {

    public abstract String typeName();

    public abstract String specifiedProperties();


    public Class<T> getTClass()
    {
        Class<T> tClass = (Class<T>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        return tClass;
    }

    @Override
    public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
        String sourceClass = "";
        if (metadata instanceof ClassMetadata) {
            sourceClass = ((ClassMetadata) metadata).getClassName();
        }
        ConditionMessage.Builder message = ConditionMessage.forCondition(typeName(), sourceClass);
        Environment environment = context.getEnvironment();
        try {
            BindResult<T> specified = Binder.get(environment).bind(specifiedProperties(), getTClass());
            if (!specified.isBound()) {
                return ConditionOutcome.match(message.because("automatic type"));
            }
            T required = ConfigurationsUtils.getType(((AnnotationMetadata) metadata).getClassName(),getTClass());
            if (specified.get() == required) {
                return ConditionOutcome.match(message.because(specified.get() + " type"));
            }
        } catch (BindException ex) {
        }
        return ConditionOutcome.noMatch(message.because("unknown type"));
    }
}
