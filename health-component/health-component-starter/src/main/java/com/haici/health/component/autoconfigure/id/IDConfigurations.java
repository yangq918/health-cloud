package com.haici.health.component.autoconfigure.id;

import com.haici.health.component.autoconfigure.common.AbstractConfigurations;
import com.haici.health.component.autoconfigure.common.ConfigurationsUtils;

/**
 * @Auther: QiaoYang
 * @Date: 2020/3/17 20:48
 * @Description:
 */
public class IDConfigurations extends AbstractConfigurations<IDGenType> {
    @Override
    protected void init() {
        this.MAPPINGS.put(IDGenType.REDIS,RedisCacheIDGenConfiguration.class);
        this.MAPPINGS.put(IDGenType.SNOWFLAKE,SnowflakeIDGenConfiguration.class);
    }
}
