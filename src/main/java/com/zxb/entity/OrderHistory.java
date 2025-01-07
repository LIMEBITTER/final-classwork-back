package com.zxb.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 *
 * @TableName tb_order_history
 */
@TableName(value ="tb_order_history")
@Data
public class OrderHistory implements Serializable {
    /**
     *
     */
    @TableId(value = "id")
    private Integer id;

    /**
     * 处理该工单的时间，也就是更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 工单的id
     */
    @TableField(value = "order_id")
    private String orderId;

    /**
     * 流转状态，e.g.拒绝，转交，同意
     */
    @TableField(value = "circulation")
    private Integer circulation;

    /**
     * 当前流程的操作人id
     */
    @TableField(value = "operator_id")
    private Integer operatorId;

    /**
     * 当前处理工单的节点 e.g.提交工单，负责人审批...
     */
    @TableField(value = "current_node")
    private String currentNode;

    /**
     * 当前流程的操作人姓名
     */
    @TableField(value = "operator_name")
    private String operatorName;

    /**
     * 当前操作人的处理意见(备注)
     */
    @TableField(value = "remark")
    private String remark;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
