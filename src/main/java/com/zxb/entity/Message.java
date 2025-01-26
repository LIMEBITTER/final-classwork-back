package com.zxb.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName tb_message
 */
@TableName(value ="tb_message")
@Data
public class Message implements Serializable {
    /**
     * 
     */
    @TableId(value = "id")
    private Integer id;

    /**
     * 
     */
    @TableField(value = "send_user")
    private String sendUser;

    /**
     * 
     */
    @TableField(value = "receive_user")
    private String receiveUser;

    /**
     * 
     */
    @TableField(value = "content")
    private String content;

    /**
     * 
     */
    @TableField(value = "is_read")
    private Integer isRead;

    /**
     * 
     */
    @TableField(value = "create_time")
    private Date createTime;

    /**
     * 
     */
    @TableField(value = "room_id")
    private Integer roomId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}