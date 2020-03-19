package com.haici.health.compoent.db.dynamic.datasource.creator.druid;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.filter.logging.Slf4jLogFilter;
import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.util.StringUtils;
import com.alibaba.druid.wall.WallConfig;
import com.alibaba.druid.wall.WallFilter;
import com.haici.health.compoent.db.dynamic.datasource.creator.DataSourceCreator;
import com.haici.health.compoent.db.dynamic.datasource.creator.DataSourceCreatorType;
import com.haici.health.compoent.db.dynamic.datasource.creator.DataSourceProperties;
import org.springframework.context.ApplicationContext;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @Auther: QiaoYang
 * @Date: 2020/3/18 17:34
 * @Description:
 */
public class DruidDataSourceCreator implements DataSourceCreator {

    private DruidConfig druidConfig = new DruidConfig();

    private ApplicationContext applicationContext;

    public DruidDataSourceCreator() {
    }

    public void setDruidConfig(DruidConfig druidConfig) {
        this.druidConfig = druidConfig;
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public DataSource createDataSource(DataSourceProperties dataSourceProperty) {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUsername(dataSourceProperty.getUsername());
        dataSource.setPassword(dataSourceProperty.getPassword());
        dataSource.setUrl(dataSourceProperty.getUrl());
        dataSource.setDriverClassName(dataSourceProperty.getDriverClassName());
        dataSource.setName(dataSourceProperty.getPollName());
        DruidConfig config = dataSourceProperty.getDruid();
        Properties properties = config.toProperties(druidConfig);
        String filters = properties.getProperty("druid.filters");
        List<Filter> proxyFilters = new ArrayList<>(2);
        if (!StringUtils.isEmpty(filters) && filters.contains("stat")) {
            StatFilter statFilter = new StatFilter();
            statFilter.configFromProperties(properties);
            proxyFilters.add(statFilter);
        }
        if (!StringUtils.isEmpty(filters) && filters.contains("wall")) {
            WallConfig wallConfig = DruidWallConfigUtil.toWallConfig(dataSourceProperty.getDruid().getWall(), druidConfig.getWall());
            WallFilter wallFilter = new WallFilter();
            wallFilter.setConfig(wallConfig);
            proxyFilters.add(wallFilter);
        }
        if (!StringUtils.isEmpty(filters) && filters.contains("slf4j")) {
            Slf4jLogFilter slf4jLogFilter = new Slf4jLogFilter();
            // 由于properties上面被用了，LogFilter不能使用configFromProperties方法，这里只能一个个set了。
            DruidConfig.DruidSlf4jConfig slf4jConfig = druidConfig.getSlf4j();
            slf4jLogFilter.setStatementLogEnabled(slf4jConfig.getEnable());
            slf4jLogFilter.setStatementExecutableSqlLogEnable(slf4jConfig.getStatementExecutableSqlLogEnable());
            proxyFilters.add(slf4jLogFilter);
        }

        if (this.applicationContext != null) {
            for (String filterId : druidConfig.getProxyFilters()) {
                proxyFilters.add(this.applicationContext.getBean(filterId, Filter.class));
            }
        }
        dataSource.setProxyFilters(proxyFilters);
        dataSource.configFromPropety(properties);
        //连接参数单独设置
        dataSource.setConnectProperties(config.getConnectionProperties());
        //设置druid内置properties不支持的的参数
        Boolean testOnReturn = config.getTestOnReturn() == null ? druidConfig.getTestOnReturn() : config.getTestOnReturn();
        if (testOnReturn != null && testOnReturn.equals(true)) {
            dataSource.setTestOnReturn(true);
        }
        Integer validationQueryTimeout =
                config.getValidationQueryTimeout() == null ? druidConfig.getValidationQueryTimeout() : config.getValidationQueryTimeout();
        if (validationQueryTimeout != null && !validationQueryTimeout.equals(-1)) {
            dataSource.setValidationQueryTimeout(validationQueryTimeout);
        }

        Boolean sharePreparedStatements =
                config.getSharePreparedStatements() == null ? druidConfig.getSharePreparedStatements() : config.getSharePreparedStatements();
        if (sharePreparedStatements != null && sharePreparedStatements.equals(true)) {
            dataSource.setSharePreparedStatements(true);
        }
        Integer connectionErrorRetryAttempts =
                config.getConnectionErrorRetryAttempts() == null ? druidConfig.getConnectionErrorRetryAttempts()
                        : config.getConnectionErrorRetryAttempts();
        if (connectionErrorRetryAttempts != null && !connectionErrorRetryAttempts.equals(1)) {
            dataSource.setConnectionErrorRetryAttempts(connectionErrorRetryAttempts);
        }
        Boolean breakAfterAcquireFailure =
                config.getBreakAfterAcquireFailure() == null ? druidConfig.getBreakAfterAcquireFailure() : config.getBreakAfterAcquireFailure();
        if (breakAfterAcquireFailure != null && breakAfterAcquireFailure.equals(true)) {
            dataSource.setBreakAfterAcquireFailure(true);
        }

        Integer timeout = config.getRemoveAbandonedTimeoutMillis() == null ? druidConfig.getRemoveAbandonedTimeoutMillis()
                : config.getRemoveAbandonedTimeoutMillis();
        if (timeout != null) {
            dataSource.setRemoveAbandonedTimeout(timeout);
        }

        Boolean abandoned = config.getRemoveAbandoned() == null ? druidConfig.getRemoveAbandoned() : config.getRemoveAbandoned();
        if (abandoned != null) {
            dataSource.setRemoveAbandoned(abandoned);
        }

        Boolean logAbandoned = config.getLogAbandoned() == null ? druidConfig.getLogAbandoned() : config.getLogAbandoned();
        if (logAbandoned != null) {
            dataSource.setLogAbandoned(logAbandoned);
        }

        try {
            dataSource.init();
        } catch (SQLException e) {
            throw new IllegalStateException("druid create error", e);
        }
        return dataSource;
    }

    @Override
    public DataSourceCreatorType getType() {
        return DataSourceCreatorType.DRUID;
    }
}
