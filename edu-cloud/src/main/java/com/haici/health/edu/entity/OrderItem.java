package com.haici.health.edu.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Auther: QiaoYang
 * @Date: 2020/3/19 15:18
 * @Description:
 */
@Data
@TableName("t_order_item")
public class OrderItem {

    @TableField("user_id")
    private Long userId;

    @TableField("order_id")
    private Long orderId;

    @TableField("item_name")
    private String itemName;

    @TableField("item_id")
    private Long itemId;
}
