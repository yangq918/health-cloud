package com.haici.health.component.cache.redis.serializer;

import lombok.extern.slf4j.Slf4j;
import org.nustaq.serialization.FSTConfiguration;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.util.Arrays;

/**
 * @Auther: QiaoYang
 * @Date: 2020/3/10 20:51
 * @Description:
 */

public class FastSerializationRedisSerializer<T> implements RedisSerializer<T> {

    private static FSTConfiguration conf = FSTConfiguration.createDefaultConfiguration();

    @Override
    public byte[] serialize(Object o) throws SerializationException {
        if (null == o) {
            return new byte[0];
        }
        try {
            byte[] ss = conf.asByteArray(o);
            return ss;
        } catch (Exception ex) {
            throw new SerializationException("Cannot serialize", ex);
        }
    }

    @Override
    public T deserialize(byte[] bytes) throws SerializationException {
        if ((bytes == null || bytes.length == 0)) {
            return null;
        }

        try {
            return (T)conf.asObject(bytes);
        } catch (Exception ex) {
            throw new SerializationException("Cannot deserialize", ex);
        }
    }

}
