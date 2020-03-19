package com.haici.health.component.autoconfigure.lock;

import com.haici.health.component.autoconfigure.common.AbstractConfigurations;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

/**
 * @Auther: QiaoYang
 * @Date: 2020/3/14 20:06
 * @Description:
 */
public class LockConfigurations extends AbstractConfigurations<LockType> {

    @Override
    protected void init() {
        MAPPINGS.put(LockType.REDIS, RedisLockConfiguration.class);
    }

}
