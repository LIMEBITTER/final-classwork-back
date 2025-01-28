package com.zxb.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName tb_order_file
 */
@TableName(value ="tb_order_file")
@Data
public class OrderFile implements Serializable {
    /**
     * 
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 
     */
    @TableField(value = "order_id")
    private Integer orderId;

    /**
     * 
     */
    @TableField(value = "file_id")
    private Integer fileId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}