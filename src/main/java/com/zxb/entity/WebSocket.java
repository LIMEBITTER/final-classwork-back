package com.zxb.entity;

import jakarta.websocket.Session;
import lombok.Data;

@Data
public class WebSocket {
    private Session session;
    private String userId;
}
