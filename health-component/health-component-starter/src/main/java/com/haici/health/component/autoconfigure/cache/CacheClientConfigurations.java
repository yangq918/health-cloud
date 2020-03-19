package com.haici.health.component.autoconfigure.cache;

import com.haici.health.component.autoconfigure.common.AbstractConfigurations;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

/**
 * @Auther: QiaoYang
 * @Date: 2020/3/14 00:25
 * @Description:
 */
public class CacheClientConfigurations  extends AbstractConfigurations<CacheClientType> {


    @Override
    protected void init() {
        MAPPINGS.put(CacheClientType.REDIS, RedisCacheClientConfiguration.class);
        MAPPINGS.put(CacheClientType.NONE, NoOpCacheClientConfiguration.class);
    }

}
