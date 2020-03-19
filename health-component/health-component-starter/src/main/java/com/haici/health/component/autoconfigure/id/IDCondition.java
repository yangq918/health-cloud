package com.haici.health.component.autoconfigure.id;

import com.haici.health.component.autoconfigure.common.AbstractTypeCondition;

/**
 * @Auther: QiaoYang
 * @Date: 2020/3/17 22:13
 * @Description:
 */
public class IDCondition extends AbstractTypeCondition<IDGenType> {
    @Override
    public String typeName() {
        return "ID";
    }

    @Override
    public String specifiedProperties() {
        return "health.component.id.type";
    }
}
