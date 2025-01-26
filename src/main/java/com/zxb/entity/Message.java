package com.zxb.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     *
     */
    @TableField(value = "room_id")
    private Integer roomId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
