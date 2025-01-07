package com.zxb.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zxb.entity.dto.Meta;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 菜单表
 * @TableName tb_permission
 *
 */
@TableName(value ="tb_permission")
@Data
@Accessors(chain = true)
public class Permission implements Serializable {

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    /**
     * 序号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 父id
     */
    @TableField(value = "parent_id")
    private Long parentId;

    /**
     * 访问路径
     */
    @TableField(value = "path")
    private String path;

    /**
     * 组件路径
     */
    @TableField(value = "component")
    private String component;

    /**
     * 图标
     */
    @TableField(value = "icon")
    private String icon;

    /**
     * 标题
     */
    @TableField(value = "title")
    private String title;

    /**
     * 创建时间
     * 插入时自动填充
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新时间
     * 更新时自动填充
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;

    /**
     * 当前状态
     */
    @TableField(value = "status")
    private Long status;

    /**
     * 权限标识
     */
    @TableField(value = "perms")
    private String perms;

    /**
     * 排序号
     */
    @TableField(value = "order_num")
    private Integer orderNum;

    /**
     * 是否隐藏
     */
    @TableField(value = "hidden")
    private Boolean hidden;

    /**
     * 菜单类型
     */
    @TableField(value = "menu_type")
    private Integer menuType;

    //权限表的子项
    @TableField(exist = false)
    private List<Permission> children = new ArrayList<>();

    @TableField(exist = false)
    private Meta meta=new Meta();
}
