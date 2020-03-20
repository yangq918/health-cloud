package com.haici.health.component.id.redis;

import com.haici.health.component.cache.redis.RedisCacheClient;
import com.haici.health.component.id.ID;
import com.haici.health.component.id.IDFormat;
import com.haici.health.component.id.IDGenerator;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Auther: QiaoYang
 * @Date: 2020/3/17 15:38
 * @Description:
 */
public class RedisCacheIDGenerator implements IDGenerator {

    private RedisCacheClient cacheClient;

    public RedisCacheIDGenerator() {
    }

    public RedisCacheIDGenerator(RedisCacheClient cacheClient) {
        this.cacheClient = cacheClient;
    }

    public void setCacheClient(RedisCacheClient cacheClient) {
        this.cacheClient = cacheClient;
    }

    @Override
    public ID next(String key) {
        long id;
        try {
            id = cacheClient.incr(key);
        } catch (Exception e) {
            throw new RuntimeException("when generat id for " + key, e);
        }
        if (id == -1) {
            throw new RuntimeException("the generate id cache has not be init.");
        }
        return new ID(id, System.currentTimeMillis());

    }

    @Override
    public ID next(String key, String format) {
        long id;
        try {
            id = cacheClient.incr(key);
            id = Long.valueOf(String.format(format, id));
        } catch (Exception e) {
            throw new RuntimeException("when generat id for " + key, e);
        }
        if (id == -1) {
            throw new RuntimeException("the generate id cache has not be init.");
        }
        return new ID(id, System.currentTimeMillis());

    }

    @Override
    public ID next(String key, IDFormat iDFormat) {
        long id;
        try {
            id = cacheClient.incr(key);
            id = iDFormat.format(id);
        } catch (Exception e) {
            throw new RuntimeException("when generat id for " + key, e);
        }
        if (id == -1) {
            throw new RuntimeException("the generate id cache has not be init.");
        }
        return new ID(id, System.currentTimeMillis());

    }

    @Override
    public ID next(String key, int timeout, IDFormat iDFormat) {
        long id;
        try {
            if (cacheClient.getIncrValue(key) == null) {
                id = cacheClient.incr(key, timeout);
            } else {
                id = cacheClient.incr(key);
            }
            id = iDFormat.format(id);
        } catch (Exception e) {
            throw new RuntimeException("when generat id for " + key, e);
        }
        if (id == -1) {
            throw new RuntimeException("the generate id cache has not be init.");
        }
        return new ID(id, System.currentTimeMillis());

    }

    @Override
    public String nextSeq(String prefix) {
        long seq = next(prefix).id;
        StringBuilder sbuf = new StringBuilder();
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        sbuf.append(prefix).append(sf.format(new Date())).append(seq);

        return sbuf.toString();
    }
}
