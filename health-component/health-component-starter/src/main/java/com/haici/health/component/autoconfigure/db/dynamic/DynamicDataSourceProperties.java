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

import com.haici.health.compoent.db.dynamic.datasource.creator.DataSourceProperties;
import com.haici.health.compoent.db.dynamic.datasource.creator.druid.DruidConfig;
import com.haici.health.compoent.db.dynamic.datasource.strategy.DynamicDataSourceStrategy;
import com.haici.health.compoent.db.dynamic.datasource.strategy.LoadBalanceDynamicDataSourceStrategy;
import com.haici.health.compoent.db.dynamic.datasource.toolkit.CryptoUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.core.Ordered;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 */
@Slf4j
@Getter
@Setter
@ConfigurationProperties(prefix = DynamicDataSourceProperties.PREFIX)
public class DynamicDataSourceProperties {

  public static final String PREFIX = "health.component.db.dynamic";
  public static final String HEALTH = PREFIX + ".health";

  /**
   * 必须设置默认的库,默认master
   */
  private String primary = "master";
  /**
   * 是否启用严格模式,默认不启动. 严格模式下未匹配到数据源直接报错, 非严格模式下则使用默认数据源primary所设置的数据源
   */
  private Boolean strict = false;
  /**
  /**
   * 是否使用 spring actuator 监控检查，默认不检查
   */
  private boolean health = false;
  /**
   * 每一个数据源
   */
  private Map<String, DataSourceProperties> datasource = new LinkedHashMap<>();
  /**
   * 多数据源选择算法clazz，默认负载均衡算法
   */
  private Class<? extends DynamicDataSourceStrategy> strategy = LoadBalanceDynamicDataSourceStrategy.class;
  /**
   * aop切面顺序，默认优先级最高
   */
  private Integer order = Ordered.HIGHEST_PRECEDENCE;
  /**
   * Druid全局参数配置
   */
  @NestedConfigurationProperty
  private DruidConfig druid = new DruidConfig();

  /**
   * 全局默认publicKey
   */
  private String publicKey = CryptoUtils.DEFAULT_PUBLIC_KEY_STRING;
}
