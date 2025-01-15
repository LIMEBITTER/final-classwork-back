package com.zxb.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * 系统公告
 * @TableName tb_notice
 */
@TableName(value ="tb_notice")
@Data
public class Notice implements Serializable {
    /**
     * 序列号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     *
     */
    @TableField(value = "title")
    private String title;

    /**
     *
     */
    @TableField(value = "publish_status")
    private Integer publishStatus;

    /**
     *
     */
    @TableField(value = "publisher_name")
    private String publisherName;

    /**
     *
     */
    @TableField(value = "level")
    private Integer level;

    /**
     *
     */
    @TableField(value = "target_type")
    private Integer targetType;

    @TableField(value = "content")
    private String content;

    /**
     * 创建时间
     */
    @JsonFormat( pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @JsonFormat( pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;

    @TableField(exist = false)
    private List<Integer> targetRoleIds;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
