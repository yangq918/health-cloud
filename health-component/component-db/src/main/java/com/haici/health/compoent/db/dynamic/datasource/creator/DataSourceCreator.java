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
package com.haici.health.compoent.db.dynamic.datasource.creator;

import javax.sql.DataSource;

/**
 * 数据源创建器
 *
 * @author TaoYu
 * @since 2.3.0
 */
public interface DataSourceCreator {


  public DataSource createDataSource(DataSourceProperties sourceCreatorProperties);


  public DataSourceCreatorType getType();

}
