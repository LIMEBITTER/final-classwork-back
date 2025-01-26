package com.zxb.utils;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.zxb.entity.Message;
import com.zxb.entity.WebSocket;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@ServerEndpoint(value = "/webSocket/{userId}")
@Slf4j
public class WebSocketTest2 {

    /**
     * 登录连接数 应该也是线程安全的
     */
    private static int loginCount = 0;
    /**
     * user 线程安全的
     */
    private static final Map<String, WebSocket> userMap = new ConcurrentHashMap<String, WebSocket>();

    /**
     * @Description: 收到消息触发事件，这个消息是连接人发送的消息
     * @Param [messageInfo, session]
     * @Return: void { "userId": "test2", "message": "你收到了嘛？这是用户test发的消息！" }
     **/
    @OnMessage
    public void onMessage(String messageInfo, Session session)
            throws IOException, InterruptedException {
        if (StringUtils.isBlank(messageInfo)) {
            return;
        }
        // 当前用户
        String userIdTo = session.getPathParameters().get("userId");
        // JSON数据
        log.info("onMessage:{}", messageInfo);
        System.out.println("接收信息:" + messageInfo + "," + userIdTo);
//    Map map = JSON.parseObject(messageInfo, Map.class);
      JSONObject jsonObject = JSONObject.parseObject(messageInfo);

      String receiveUser = jsonObject.getString("receiveUser");
        System.out.println("recceiii"+receiveUser);
//    // 接收人
//    String userId = (String) map.get("userId");
//    // 消息内容
//    String message = (String) map.get("message");

        // 发送给指定用户
        sendMessageTo(messageInfo, receiveUser);
        log.info(DateUtil.now() + " | " + userIdTo + " 私人消息-> " + messageInfo, userIdTo);
    }

    /**
     * @Description: 打开连接触发事件
     * @Param [account, session, config]
     * @Return: void
     **/
    @OnOpen
    public void onOpen(@PathParam("userId") String userId, Session session) throws IOException {
        WebSocket webSocket = new WebSocket();
        webSocket.setUserId(userId);
        webSocket.setSession(session);
        boolean containsKey = userMap.containsKey(userId);
        if (!containsKey) {
            // 添加登录用户数量
            addLoginCount();
            userMap.put(userId, webSocket);
        }
        log.info("打开连接触发事件!已连接用户: " + userId);
        log.info("当前在线人数: " + loginCount);
        Message message = new Message();
        message.setContent(userId + " 已连接");
        sendMessageAll(JSONObject.toJSONString(message));
    }

    /**
     * @Description: 关闭连接触发事件
     * @Param [session, closeReason]
     * @Return: void
     **/
    @OnClose
    public void onClose(@PathParam("userId") String userId, Session session,
                        CloseReason closeReason) throws IOException {
        boolean containsKey = userMap.containsKey(userId);
        if (containsKey) {
            // 删除map中用户
            userMap.remove(userId);
            // 减少断开连接的用户
            reduceLoginCount();
        }
        log.info("关闭连接触发事件!已断开用户: " + userId);
        log.info("当前在线人数: " + loginCount);
        Message message = new Message();
        message.setContent(userId + " 已断开");
        sendMessageAll(JSONObject.toJSONString(message));
    }

    /**
     * @Description: 传输消息错误触发事件
     * @Param [error ：错误]
     * @Return: void
     **/
    @OnError
    public void onError(Throwable error) {
        log.info("onError:{}", error.getMessage());
    }

    /**
     * @Description: 发送指定用户信息
     * @Param [message：信息, userId：用户]
     * @Return: void
     **/
    public void sendMessageTo(String message, String userId) throws IOException {
        for (WebSocket user : userMap.values()) {
            System.out.println(user+"dangqdfd"+userMap.values());

            if (user.getUserId().equals(userId)) {
                System.out.println("用户:" + userId + "收到信息:" + message);
                user.getSession().getAsyncRemote().sendText(message);
            }
        }
    }

    /**
     * @Description: 发给所有人
     * @Param [message：信息]
     * @Return: void
     **/
    public void sendMessageAll(String message) throws IOException {
        for (WebSocket item : userMap.values()) {
            item.getSession().getAsyncRemote().sendText(message);
        }
    }

    /**
     * @Description: 连接登录数增加
     * @Param []
     * @Return: void
     **/
    public static synchronized void addLoginCount() {
        loginCount++;
    }

    /**
     * @Description: 连接登录数减少
     * @Param []
     * @Return: void
     **/
    public static synchronized void reduceLoginCount() {
        loginCount--;
    }

    /**
     * @Description: 获取用户
     * @Param []
     * @Return: java.util.Map<java.lang.String, com.cn.webSocket.WebSocket>
     **/
    public synchronized Map<String, WebSocket> getUsers() {
        return userMap;
    }

}
