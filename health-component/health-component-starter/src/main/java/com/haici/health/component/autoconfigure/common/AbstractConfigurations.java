package com.haici.health.component.autoconfigure.common;

import com.haici.health.component.autoconfigure.lock.LockType;
import org.springframework.util.Assert;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: QiaoYang
 * @Date: 2020/3/17 20:54
 * @Description:
 */
public abstract class AbstractConfigurations<T extends Enum> implements Configurations<T> {

    protected Map<T, Class<?>> MAPPINGS = new HashMap<>();


    public AbstractConfigurations() {
        init();
    }

    protected abstract void init();


    @Override
    public String getConfigurationClass(T type) {
        Class<?> configurationClass = MAPPINGS.get(type);
        Assert.state(configurationClass != null, () -> "Unknown type " + type);
        return configurationClass.getName();
    }

    @Override
    public T getType(String configurationClassName) {
        for (Map.Entry<T, Class<?>> entry : MAPPINGS.entrySet()) {
            if (entry.getValue().getName().equals(configurationClassName)) {
                return entry.getKey();
            }
        }
        throw new IllegalStateException("Unknown configuration class " + configurationClassName);
    }

}
