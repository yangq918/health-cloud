package com.haici.health.edu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.haici.health.compoent.db.dynamic.datasource.annotation.DS;
import com.haici.health.edu.entity.Order;

/**
 * @Auther: QiaoYang
 * @Date: 2020/3/19 15:20
 * @Description:
 */
@DS("edu-sharding-jdbc")
public interface OrderMapper extends BaseMapper<Order> {
}
