package com.zxb.utils;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.zxb.entity.SocketMsg;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Component
@ServerEndpoint("/test/websocket/{userName}")
public class WebSocketTest {
    private String userName;
    private Session session;

    /**
     * 固定前缀
     */
    private static final String USER_NAME_PREFIX = "user_name_";

    /**
     * 存放Session集合，方便推送消息 （jakarta.websocket）
     */
    private static ConcurrentHashMap<String, Session> sessionMap = new ConcurrentHashMap<>();

    /**
     * 存放房间用户集合，方便推送消息 （jakarta.websocket）
     */
    private static HashMap<String, List<String>> groupSessionMap = new HashMap<>();

    /**
     * 群聊：向指定房间ID推送消息
     */
    public synchronized static void groupMessage(SocketMsg socketMsg) {
        // 存储房间号和用户信息
        String roomId = socketMsg.getRoomId();
        // 判断是否有这个房间
        List<String> strings = groupSessionMap.get(roomId);
        if (ObjectUtil.isEmpty(strings)) {
            List<String> users = new ArrayList<>();
            users.add(socketMsg.getSendOutUser());
            groupSessionMap.put(roomId, users);
        } else {
            // 这里应该写接口，先添加房间ID，简易写法直接传过来
            List<String> users = groupSessionMap.get(roomId);
            String sendOutUser = socketMsg.getSendOutUser();
            boolean contains = users.contains(sendOutUser);
            if (!contains) {
                users.add(sendOutUser);
            }
        }

        // 发送给接收者
        if (roomId != null) {
            // 发送给接收者
            System.out.println(socketMsg.getSendOutUser() + " 向房间 【 " + roomId + " 】 发送了一条消息：" + socketMsg.getMsg());
            // 此时要判断房间有哪些人，把这些消息定向发给处于此房间的用户
            List<String> roomUser = groupSessionMap.get(roomId);
            for (String userName : roomUser) {
                // 接收消息的用户
                Session receiveUser = sessionMap.get(USER_NAME_PREFIX + userName);
                receiveUser.getAsyncRemote().sendText(socketMsg.getSendOutUser() + " 向房间 【 " + roomId + " 】 发送了一条消息：" + socketMsg.getMsg());
            }
        } else {
            // 发送消息的用户
            System.out.println(socketMsg.getSendOutUser() + " 私聊的用户 " + socketMsg.getReceiveUser() + " 不在线或者输入的用户名不对");
            Session sendOutUser = sessionMap.get(USER_NAME_PREFIX + socketMsg.getSendOutUser());
            // 将系统提示推送给发送者
            sendOutUser.getAsyncRemote().sendText("系统消息：对方不在线或者您输入的用户名不对");
        }
    }

    /**
     * 私聊：向指定客户端推送消息
     */
    public synchronized static void privateMessage(SocketMsg socketMsg) {
        // 接收消息的用户
        Session receiveUser = sessionMap.get(USER_NAME_PREFIX + socketMsg.getReceiveUser());
        // 发送给接收者
        if (receiveUser != null) {
            // 发送给接收者
            System.out.println(socketMsg.getSendOutUser() + " 向 " + socketMsg.getReceiveUser() + " 发送了一条消息：" + socketMsg.getMsg());
            receiveUser.getAsyncRemote().sendText(socketMsg.getSendOutUser() + "：" + socketMsg.getMsg());
        } else {
            // 发送消息的用户
            System.out.println(socketMsg.getSendOutUser() + " 私聊的用户 " + socketMsg.getReceiveUser() + " 不在线或者输入的用户名不对");
            Session sendOutUser = sessionMap.get(USER_NAME_PREFIX + socketMsg.getSendOutUser());
            // 将系统提示推送给发送者
            sendOutUser.getAsyncRemote().sendText("系统消息：对方不在线或者您输入的用户名不对");
        }
    }

    /**
     * 全局消息：公开聊天记录
     *
     * @param message 发送的消息
     */
    public synchronized static void publicMessage(String message) {
        for (String username : sessionMap.keySet()) {
            Session session = sessionMap.get(username);
            session.getAsyncRemote().sendText("公共频道接收了一条消息：" + message);
            System.out.println("公共频道接收了一条消息：" + message);
        }
    }

    /**
     * 监听：连接成功
     *
     * @param session
     * @param userName 连接的用户名
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("userName") String userName) {
        this.userName = userName;
        this.session = session;
        String name = USER_NAME_PREFIX + userName;
        if (!sessionMap.containsKey(name)) {
            sessionMap.put(name, session);
            // 在线数加1
            String tips = userName + " 加入聊天室。当前聊天室人数为" + sessionMap.size();
            System.out.println(tips);
            publicMessage(tips);
        }
    }

    /**
     * 监听: 连接关闭
     */
    @OnClose
    public void onClose() {
        String name = USER_NAME_PREFIX + userName;
        // 连接关闭后，将此websocket从set中删除
        sessionMap.remove(name);
        String tips = userName + " 退出聊天室。当前聊天室人数为" + sessionMap.size();
        System.out.println(tips);
        publicMessage(tips);
    }

    /**
     * 监听：收到客户端发送的消息
     *
     * @param message 发送的信息（json格式，里面是 SocketMsg 的信息）
     */
    @OnMessage
    public void onMessage(String message) {
        if (JSONUtil.isTypeJSONObject(message)) {
            SocketMsg socketMsg = JSONUtil.toBean(message, SocketMsg.class);
            if (socketMsg.getType() == 2) {
                // 群聊，需要找到发送者和房间ID
                groupMessage(socketMsg);
            } else if (socketMsg.getType() == 1) {
                // 单聊，需要找到发送者和接受者
                privateMessage(socketMsg);
            } else {
                // 全局广播群发消息
                publicMessage(socketMsg.getMsg());
            }
        }
    }

    /**
     * 监听：发生异常
     *
     * @param error
     */
    @OnError
    public void onError(Throwable error) {
        System.out.println("userName为：" + userName + "，发生错误：" + error.getMessage());
        error.printStackTrace();
    }


}
