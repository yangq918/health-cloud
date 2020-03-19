/**
 * Copyright © 2018 organization baomidou
 * <pre>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * <pre/>
 */
package com.haici.health.component.autoconfigure.db.dynamic;

import com.alibaba.cloud.nacos.NacosConfigManager;
import com.haici.health.compoent.db.dynamic.datasource.creator.DataSourceCreator;
import com.haici.health.compoent.db.dynamic.datasource.creator.DataSourceCreatorHolder;
import com.haici.health.compoent.db.dynamic.datasource.creator.DataSourceCreatorType;
import com.haici.health.compoent.db.dynamic.datasource.creator.druid.DruidDataSourceCreator;
import com.haici.health.compoent.db.dynamic.datasource.creator.shardingjdbc.ShardingjdbcDataSourceCreator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Configuration
@AllArgsConstructor
@EnableConfigurationProperties(DynamicDataSourceProperties.class)
public class DynamicDataSourceCreatorAutoConfiguration {

    @Autowired
    private DynamicDataSourceProperties properties;

    @Autowired
    private ApplicationContext applicationContext;


    @Bean
    @ConditionalOnMissingBean
    public DataSourceCreatorHolder dataSourceCreatorHolder(ObjectProvider<DataSourceCreator[]> dataSourceCreatorProvider) {
        DataSourceCreatorHolder dataSourceCreatorHolder = new DataSourceCreatorHolder();
        DataSourceCreator[] creators = dataSourceCreatorProvider.getIfAvailable();
        if (null != creators) {
            Arrays.stream(creators).forEach(ele -> {
                dataSourceCreatorHolder.addCreator(ele);
            });
        }
        return dataSourceCreatorHolder;
    }


    @Bean
    @ConditionalOnMissingBean
    public DruidDataSourceCreator druidDataSourceCreator() {
        DruidDataSourceCreator druidDataSourceCreator = new DruidDataSourceCreator();
        druidDataSourceCreator.setDruidConfig(properties.getDruid());
        druidDataSourceCreator.setApplicationContext(applicationContext);
        return druidDataSourceCreator;
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnBean(NacosConfigManager.class)
    public ShardingjdbcDataSourceCreator shardingjdbcDataSourceCreator(NacosConfigManager nacosConfigManager) {
        ShardingjdbcDataSourceCreator creator = new ShardingjdbcDataSourceCreator();
        creator.setConfigService(nacosConfigManager.getConfigService());
        return creator;
    }
}
