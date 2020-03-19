package com.haici.health.compoent.db.dynamic.datasource.creator;

import com.haici.health.compoent.db.dynamic.datasource.creator.druid.DruidDataSourceCreator;
import com.haici.health.compoent.db.dynamic.datasource.creator.shardingjdbc.ShardingjdbcDataSourceCreator;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Objects;

/**
 * @Auther: QiaoYang
 * @Date: 2020/3/18 15:54
 * @Description:
 */
public enum DataSourceCreatorType {

    DRUID("druid"),

    SHARDINGJDBC("shardingjdbc");

    private String code;


    DataSourceCreatorType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }


    public static DataSourceCreatorType findByCode(String code) {
        DataSourceCreatorType[] types = DataSourceCreatorType.values();
        return Arrays.stream(types).filter(dataSourceCreatorType -> Objects.equals(code, dataSourceCreatorType.code)).findFirst().orElse(null);
    }


}
