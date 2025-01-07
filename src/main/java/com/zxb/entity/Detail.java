package com.zxb.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 *
 * @TableName tb_detail
 */
@TableName(value ="tb_detail")
@Data
public class Detail implements Serializable {
    /**
     *
     */
    @TableId(value = "detail_id")
    private Integer detailId;

    /**
     * 20位工单id
     */
    @TableField(value = "order_id")
    private String orderId;

    /**
     * 上传的图片url
     */
    @TableField(value = "image_url")
    private String imageUrl;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
