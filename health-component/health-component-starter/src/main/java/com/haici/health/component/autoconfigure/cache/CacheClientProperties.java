package com.haici.health.component.autoconfigure.cache;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Auther: QiaoYang
 * @Date: 2020/3/14 00:02
 * @Description:
 */
@ConfigurationProperties(prefix = "health.component.cache-client")
@Data
public class CacheClientProperties {

    CacheClientType type;


    private final Redis redis = new Redis();



    @Data
    public static class Redis {

        /**
         * 前缀
         */
        private String prefix;

        /**
         * 缓存时间
         */
        private int  cacheTime = 60 * 60 * 1;


        private RedisSerializerType serializerType;

    }

}
