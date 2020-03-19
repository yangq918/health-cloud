package com.haici.health.component.autoconfigure.id;

import com.haici.health.component.autoconfigure.cache.CacheClientType;
import com.haici.health.component.autoconfigure.cache.RedisSerializerType;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Auther: QiaoYang
 * @Date: 2020/3/17 18:44
 * @Description:
 */
@ConfigurationProperties(prefix = "health.component.id")
@Data
public class IDProperties {

    private IDGenType type;

    private Redis redis = new Redis();

    private Snowflake snowflake = new Snowflake();


    @Data
    public static class Redis {



    }


    @Data
    public static class Snowflake {

        private String nacosAddress;

        private String idName;

        private String idGroup;

    }


}
