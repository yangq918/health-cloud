package com.haici.health.component.autoconfigure.common;

import com.haici.health.component.autoconfigure.cache.CacheClientConfigurations;
import com.haici.health.component.autoconfigure.cache.CacheClientType;
import com.haici.health.component.autoconfigure.id.IDConfigurations;
import com.haici.health.component.autoconfigure.id.IDGenType;
import com.haici.health.component.autoconfigure.lock.LockConfigurations;
import com.haici.health.component.autoconfigure.lock.LockType;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: QiaoYang
 * @Date: 2020/3/17 21:06
 * @Description:
 */
public abstract class ConfigurationsUtils {

    private static final Map<Class<?>, AbstractConfigurations<?>> CONFIGURATIONS_MAP = new HashMap<>();

    static {
        CONFIGURATIONS_MAP.put(IDGenType.class,new IDConfigurations());
        CONFIGURATIONS_MAP.put(CacheClientType.class,new CacheClientConfigurations());
        CONFIGURATIONS_MAP.put(LockType.class,new LockConfigurations());
    }




    public static <T extends Enum> String getConfigurationClass(T type) {
        AbstractConfigurations<T> configurations = (AbstractConfigurations<T>) CONFIGURATIONS_MAP.get(type.getClass());
        Assert.state(configurations != null, () -> "Unknown type " + type);
        return configurations.getConfigurationClass(type);
    }

    public static <T extends Enum> T getType(String configurationClassName, Class<T> clz) {

        AbstractConfigurations<T> configurations = (AbstractConfigurations<T>) CONFIGURATIONS_MAP.get(clz);
        Assert.state(configurations != null, () -> "Unknown configuration clas type " + configurationClassName);
        return configurations.getType(configurationClassName);
    }
}
