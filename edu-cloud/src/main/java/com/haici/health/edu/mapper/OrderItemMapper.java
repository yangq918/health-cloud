package com.haici.health.edu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.haici.health.compoent.db.dynamic.datasource.annotation.DS;
import com.haici.health.edu.entity.OrderItem;

/**
 * @Auther: QiaoYang
 * @Date: 2020/3/19 15:21
 * @Description:
 */
@DS("edu-sharding-jdbc")
public interface OrderItemMapper extends BaseMapper<OrderItem> {
}
