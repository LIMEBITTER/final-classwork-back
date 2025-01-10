package com.zxb.entity;


import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.annotation.Transient;

/**
 *
 * @TableName tb_comment
 */
@TableName(value ="tb_comment")
@Data
public class Comment implements Serializable {
    /**
     *
     */
    @TableId(value = "id")
    private Integer id;

    /**
     * 评论人id
     */
    @TableField(value = "order_id")
    private String orderId;

    /**
     *
     */
    @TableField(value = "user_id")
    private Integer userId;

    /**
     * 父评论id，默认一级评论为0
     */
    @TableField(value = "parent_id")
    private Integer parentId;

    /**
     *
     */
    @TableField(value = "content")
    private String content;

    /**
     *
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     *
     */
    @TableField(value = "role_id")
    private Integer roleId;



    @TableField(exist = false)
    private List<Comment> children; //评论集合，用于渲染子评论

    @TableField(exist = false)
    private String avatar; //展示评论人头像

    @TableField(exist = false)
    private String reply;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @TableField(exist = false)
    private String userName; //展示评论人姓名
}
