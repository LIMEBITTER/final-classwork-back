package com.zxb.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 *
 * @TableName tb_comeback
 */
@TableName(value ="tb_comeback")
@Data
public class Comeback implements Serializable {
    /**
     * 评论id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 评论内容
     */
    @TableField(value = "content")
    private String content;

    /**
     * 评论人id
     */
    @TableField(value = "user_id")
    private Integer userId;

    /**
     * 评论人姓名
     */
    @TableField(value = "user_name")
    private String userName;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 是否已删除
     */
    @TableField(value = "is_delete")
    private Integer isDelete;

    /**
     * 所属工单id
     */
    @TableField(value = "order_id")
    private String orderId;

    /**
     * 父评论id
     */
    @TableField(value = "parent_id")
    private Integer parentId;

    /**
     * 根评论id
     */
    @TableField(value = "root_parent_id")
    private Integer rootParentId;

    @TableField(exist = false)
    private List<Comeback> children; // 本评论下的子评论

    @TableField(exist = false)
    private String avatar;

    @TableField(exist = false)
    private List<String> role;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
