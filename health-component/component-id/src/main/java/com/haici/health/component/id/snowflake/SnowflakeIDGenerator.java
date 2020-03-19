package com.haici.health.component.id.snowflake;

import com.haici.health.component.id.ID;
import com.haici.health.component.id.IDFormat;
import com.haici.health.component.id.IDGenerator;
import com.haici.health.component.id.snowflake.support.SnowflakeWorker;
import org.springframework.util.IdGenerator;

import java.util.List;

/**
 * @Auther: QiaoYang
 * @Date: 2020/3/16 16:36
 * @Description:
 */
public class SnowflakeIDGenerator implements IDGenerator {


    private SnowflakeWorker worker;


    @Override
    public ID next(String key) {
        return worker.get();
    }

    @Override
    public ID next(String key, String format) {
        return worker.get();
    }

    @Override
    public ID next(String key, IDFormat iDFormat) {
        return worker.get();
    }

    @Override
    public ID next(String key, int timeout, IDFormat iDFormat) {
        return worker.get();
    }

    public static SnowflakeIDGeneratorBuilder builder(SnowflakeWorker snowflakeWorker) {
        return new SnowflakeIDGeneratorBuilder(snowflakeWorker);
    }


    public static class SnowflakeIDGeneratorBuilder {
        private SnowflakeWorker snowflakeWorker;

        public SnowflakeIDGeneratorBuilder(SnowflakeWorker snowflakeWorker) {
            this.snowflakeWorker = snowflakeWorker;
        }

        public SnowflakeIDGenerator build() {
            SnowflakeIDGenerator snowflakeIDGenerator = new SnowflakeIDGenerator();
            snowflakeIDGenerator.worker = snowflakeWorker;
            return snowflakeIDGenerator;
        }
    }

}
