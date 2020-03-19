package com.haici.health.component.autoconfigure.common;

import com.haici.health.component.autoconfigure.lock.LockType;
import org.springframework.util.Assert;

import java.util.Map;

/**
 * @Auther: QiaoYang
 * @Date: 2020/3/17 20:50
 * @Description:
 */
public interface Configurations<T> {

    String getConfigurationClass(T type);

    T getType(String configurationClassName);
}
