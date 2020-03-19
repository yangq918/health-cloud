package com.haici.health.edu.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Auther: QiaoYang
 * @Date: 2020/3/19 15:17
 * @Description:
 */
@Data
@TableName("t_order")
public class Order {

    @TableField("user_id")
    private Long userId;

    @TableField("order_id")
    private Long orderId;
}
