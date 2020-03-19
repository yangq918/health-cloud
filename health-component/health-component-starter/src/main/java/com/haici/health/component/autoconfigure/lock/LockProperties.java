package com.haici.health.component.autoconfigure.lock;

import com.haici.health.component.autoconfigure.cache.CacheClientType;
import com.haici.health.component.autoconfigure.cache.RedisSerializerType;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Auther: QiaoYang
 * @Date: 2020/3/14 00:02
 * @Description:
 */
@ConfigurationProperties(prefix = "health.component.lock")
@Data
public class LockProperties {

    LockType type;


    private final Redis redis = new Redis();



    @Data
    public static class Redis {

        /**
         * 锁前缀
         */
        private String prefix;

        private long retryAwait = 300L;

        private long lockTimeout = 2000L;

    }

}
