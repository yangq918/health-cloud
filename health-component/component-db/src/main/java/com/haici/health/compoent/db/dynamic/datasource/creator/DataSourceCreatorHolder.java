package com.haici.health.compoent.db.dynamic.datasource.creator;

import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: QiaoYang
 * @Date: 2020/3/19 11:39
 * @Description:
 */
public class DataSourceCreatorHolder {

    private Map<DataSourceCreatorType,DataSourceCreator> creatorMap = new HashMap<>();


    public void addCreator(DataSourceCreator creator)
    {
        creatorMap.put(creator.getType(),creator);
    }


    public DataSourceCreator getCreator(DataSourceCreatorType type)
    {
        return creatorMap.get(type);
    }

}
