package com.zxb.entity;

import lombok.Data;


/**
 * @author zxb
 * @date 2025/1/26
 * @description 实体类 接收客户端发过来的信息
 */
@Data
public class SocketMsg {
    /**
     * 聊天类型 0 全局广播 1 单聊 2 群聊
     **/
    private Integer type;
    /**
     * 发送者
     **/
    private String sendOutUser;
    /**
     * 接受者
     **/
    private String receiveUser;
    /**
     * 房间id
     **/
    private String roomId;
    /**
     * 消息
     **/
    private String msg;

}


