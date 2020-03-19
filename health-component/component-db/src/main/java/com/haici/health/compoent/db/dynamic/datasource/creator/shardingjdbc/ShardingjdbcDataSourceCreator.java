package com.haici.health.compoent.db.dynamic.datasource.creator.shardingjdbc;

import com.alibaba.nacos.api.config.ConfigService;
import com.haici.health.compoent.db.dynamic.datasource.creator.DataSourceCreator;
import com.haici.health.compoent.db.dynamic.datasource.creator.DataSourceCreatorType;
import com.haici.health.compoent.db.dynamic.datasource.creator.DataSourceProperties;
import org.apache.shardingsphere.shardingjdbc.api.yaml.YamlShardingDataSourceFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;

/**
 * @Auther: QiaoYang
 * @Date: 2020/3/18 18:05
 * @Description:
 */
public class ShardingjdbcDataSourceCreator implements DataSourceCreator {


    private ConfigService configService;

    public void setConfigService(ConfigService configService) {
        this.configService = configService;
    }

    @Override
    public DataSource createDataSource(DataSourceProperties sourceCreatorProperties) {

        try {
            ShardingjdbcConfig.Nacos nacos = sourceCreatorProperties.getShardingjdbc().getNacos();
            String content = configService.getConfig(nacos.getDataId(), nacos.getGroup(), 5000);

            return YamlShardingDataSourceFactory.createDataSource(content.getBytes());
        } catch (Exception e) {
            throw new IllegalStateException("druid create error", e);
        }

    }

    @Override
    public DataSourceCreatorType getType() {
        return DataSourceCreatorType.SHARDINGJDBC;
    }
}
