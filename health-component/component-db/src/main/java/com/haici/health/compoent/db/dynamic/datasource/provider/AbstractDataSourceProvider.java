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
package com.haici.health.compoent.db.dynamic.datasource.provider;

import com.haici.health.compoent.db.dynamic.datasource.creator.DataSourceCreatorHolder;
import com.haici.health.compoent.db.dynamic.datasource.creator.DataSourceCreatorType;
import com.haici.health.compoent.db.dynamic.datasource.creator.DataSourceProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;


@Slf4j
public abstract class AbstractDataSourceProvider implements DynamicDataSourceProvider {


  @Setter
  @Getter
  protected DataSourceCreatorHolder holder;


  protected Map<String, DataSource> createDataSourceMap(
          Map<String, DataSourceProperties> dataSourcePropertiesMap) {
    Map<String, DataSource> dataSourceMap = new HashMap<>(dataSourcePropertiesMap.size() * 2);
    for (Map.Entry<String, DataSourceProperties> item : dataSourcePropertiesMap.entrySet()) {
      DataSourceProperties dataSourceProperty = item.getValue();
      String pollName = dataSourceProperty.getPollName();
      if (pollName == null || "".equals(pollName)) {
        pollName = item.getKey();
      }
      dataSourceProperty.setPollName(pollName);
      DataSourceCreatorType creatorType = DataSourceCreatorType.findByCode(dataSourceProperty.getType());
      dataSourceMap.put(pollName, holder.getCreator(creatorType).createDataSource(dataSourceProperty));
    }
    return dataSourceMap;
  }
}
