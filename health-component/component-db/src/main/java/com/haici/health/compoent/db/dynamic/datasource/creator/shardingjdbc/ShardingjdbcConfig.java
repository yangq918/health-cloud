package com.haici.health.compoent.db.dynamic.datasource.creator.shardingjdbc;

import lombok.Data;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * @Auther: QiaoYang
 * @Date: 2020/3/18 18:06
 * @Description:
 */
@Data
public class ShardingjdbcConfig {

    private byte[] ymlBytes;

    @NestedConfigurationProperty
    private Nacos nacos = new Nacos();

    @Data
    public static class Nacos {
        private String dataId;

        private String group;
    }


}
