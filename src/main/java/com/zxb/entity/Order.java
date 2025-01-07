package com.zxb.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 *
 * @TableName tb_order
 */
@TableName(value ="tb_order")
@Data
public class Order implements Serializable {
    /**
     *
     */
    @TableId(value = "id")
    private Integer id;

    /**
     *
     */
    @TableField(value = "title")
    private String title;

    /**
     * 当前工单优先级
     */
    @TableField(value = "priority")
    private Integer priority;

    /**
     * 当前流转到的处理人
     */
    @TableField(value = "related_person")
    private Integer relatedPerson;

    /**
     * 工单当前状态
     */
    @TableField(value = "state")
    private Integer state;

    /**
     * 提交工单的人
     */
    @TableField(value = "creator_id")
    private Integer creatorId;

    /**
     *
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     *
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;

    /**
     * 当前工单的详细信息
     */
    @TableField(value = "detail_id")
    private Integer detailId;

    /**
     *
     */
    @TableField(value = "order_id")
    private String orderId;

    /**
     *
     */
    @TableField(value = "complaint")
    private String complaint;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
