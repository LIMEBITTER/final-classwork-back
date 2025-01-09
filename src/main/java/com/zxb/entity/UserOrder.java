package com.zxb.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName tb_user_order
 */
@TableName(value ="tb_user_order")
@Data
public class UserOrder implements Serializable {
    /**
     * 
     */
    @TableId(value = "id")
    private Integer id;

    /**
     * 
     */
    @TableField(value = "alloc_user_id")
    private Integer allocUserId;

    /**
     * 
     */
    @TableField(value = "order_id")
    private String orderId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}