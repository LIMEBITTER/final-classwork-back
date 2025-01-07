package com.zxb.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 文件上传
 * @TableName tb_file
 */
@TableName(value ="tb_file")
@Accessors(chain = true)
@Data
public class Files implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    /**
     * 序号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 文件名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 文件类型
     */
    @TableField(value = "type")
    private String type;

    /**
     * 文件大小
     */
    @TableField(value = "size")
    private Long size;

    /**
     * 是否禁用
     */
    @TableField(value = "enable")
    private Integer enable;

    /**
     * 访问路径
     */
    @TableField(value = "url")
    private String url;

    /**
     * md5
     */
    @TableField(value = "md5")
    private String md5;

    /**
     * 是否删除
     */
    @TableField(value = "is_delete")
    private Boolean isDelete;


}
