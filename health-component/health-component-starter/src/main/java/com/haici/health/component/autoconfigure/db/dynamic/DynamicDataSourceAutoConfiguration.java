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

import com.haici.health.compoent.db.dynamic.datasource.DynamicRoutingDataSource;
import com.haici.health.compoent.db.dynamic.datasource.aop.DynamicDataSourceAnnotationAdvisor;
import com.haici.health.compoent.db.dynamic.datasource.aop.DynamicDataSourceAnnotationInterceptor;
import com.haici.health.compoent.db.dynamic.datasource.creator.DataSourceCreatorHolder;
import com.haici.health.compoent.db.dynamic.datasource.creator.DataSourceProperties;
import com.haici.health.compoent.db.dynamic.datasource.processor.DsProcessor;
import com.haici.health.compoent.db.dynamic.datasource.processor.DsSpelExpressionProcessor;
import com.haici.health.compoent.db.dynamic.datasource.provider.DynamicDataSourceProvider;
import com.haici.health.compoent.db.dynamic.datasource.provider.YmlDynamicDataSourceProvider;
import com.haici.health.compoent.db.dynamic.datasource.strategy.DynamicDataSourceStrategy;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.sql.DataSource;
import java.util.Map;

/**
 *
 */
@Slf4j
@Configuration
@AllArgsConstructor
@EnableConfigurationProperties(DynamicDataSourceProperties.class)
@AutoConfigureBefore(DataSourceAutoConfiguration.class)
@Import(value = {DynamicDataSourceCreatorAutoConfiguration.class})
@ConditionalOnProperty(prefix = DynamicDataSourceProperties.PREFIX, name = "enabled", havingValue = "true", matchIfMissing = true)
public class DynamicDataSourceAutoConfiguration {

  private final DynamicDataSourceProperties properties;

  @Bean
  @ConditionalOnMissingBean
  public DynamicDataSourceProvider dynamicDataSourceProvider(DataSourceCreatorHolder dataSourceCreatorHolder) {
    Map<String, DataSourceProperties> datasourceMap = properties.getDatasource();
    YmlDynamicDataSourceProvider dynamicDataSourceProvider = new  YmlDynamicDataSourceProvider(datasourceMap);
    dynamicDataSourceProvider.setHolder(dataSourceCreatorHolder);
    return dynamicDataSourceProvider;
  }

  @Bean
  @ConditionalOnMissingBean
  public DynamicDataSourceStrategy dynamicDataSourceStrategy() throws Exception {
    return properties.getStrategy().newInstance();
  }

  @Bean
  @ConditionalOnMissingBean
  public DataSource dataSource(DynamicDataSourceProvider dynamicDataSourceProvider, DynamicDataSourceStrategy dynamicDataSourceStrategy) {
    DynamicRoutingDataSource dataSource = new DynamicRoutingDataSource();
    dataSource.setPrimary(properties.getPrimary());
    dataSource.setStrict(properties.getStrict());
    dataSource.setStrategy(dynamicDataSourceStrategy);
    dataSource.setProvider(dynamicDataSourceProvider);
    return dataSource;
  }

  @Bean
  @ConditionalOnMissingBean
  public DynamicDataSourceAnnotationAdvisor dynamicDatasourceAnnotationAdvisor(DsProcessor dsProcessor) {
    DynamicDataSourceAnnotationInterceptor interceptor = new DynamicDataSourceAnnotationInterceptor();
    interceptor.setDsProcessor(dsProcessor);
    DynamicDataSourceAnnotationAdvisor advisor = new DynamicDataSourceAnnotationAdvisor(interceptor);
    advisor.setOrder(properties.getOrder());
    return advisor;
  }

  @Bean
  @ConditionalOnMissingBean
  public DsProcessor dsProcessor() {
    DsSpelExpressionProcessor spelExpressionProcessor = new DsSpelExpressionProcessor();
    return spelExpressionProcessor;
  }

}
