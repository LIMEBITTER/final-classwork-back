package com.zxb.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zxb.common.Result;
import com.zxb.entity.Message;
import com.zxb.entity.User;
import com.zxb.entity.WebSocket;
import com.zxb.entity.dto.MessageForm;
import com.zxb.service.MessageService;
import com.zxb.mapper.MessageMapper;
import com.zxb.service.UserService;
import com.zxb.utils.WebSocketTest2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
* @author zxb
* @description 针对表【tb_message】的数据库操作Service实现
* @createDate 2025-01-26 15:46:41
*/
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message>
    implements MessageService{

    private final UserService userService;
    private final MessageMapper messageMapper;
    private final WebSocketTest2 webSocketUtil;

    @Autowired
    public MessageServiceImpl(UserService userService,MessageMapper messageMapper,WebSocketTest2 webSocketUtil){
        this.userService = userService;
        this.messageMapper = messageMapper;
        this.webSocketUtil = webSocketUtil;
    }

    @Override
    @Transactional
    public List<Message> findMsgByTwoPerson(String sendUserId, String receiveUserId) {

        List<Message> allMessageList = new ArrayList<>();

        if (sendUserId.isEmpty()||receiveUserId.isEmpty()){
            return new ArrayList<>();
        }
        User sendUser = userService.getById(sendUserId);
        if (ObjectUtil.isNull(sendUser)){
            return new ArrayList<>();
        }
        User receiveUser = userService.getById(receiveUserId);
        if (ObjectUtil.isNull(receiveUser)){
            return new ArrayList<>();
        }
        //获取对方发送的信息
        List<Message> receiveMessageList = messageMapper.selectBySendUserAndReceiveUser(sendUserId, receiveUserId);

        //获取发送给对方的信息
        List<Message> sendMessageList = messageMapper.selectBySendUserAndReceiveUser(receiveUserId, sendUserId);


        allMessageList.addAll(receiveMessageList);
        allMessageList.addAll(sendMessageList);

        List<Message> sortedMessageList = allMessageList.stream()
                .sorted(Comparator.comparing(Message::getCreateTime))
                .toList();

        System.out.println("sort"+sortedMessageList);

        //todo 设置已读，暂时没做
        List<Message> noReadMessageList = receiveMessageList.stream()
                .filter(o->o.getIsRead().equals(0))
                .peek(message->message.setIsRead(1))
                .toList();

        //未读变已读
        if (!noReadMessageList.isEmpty()){
            this.updateBatchById(noReadMessageList);
        }

        return sortedMessageList;

    }

    @Override
    public Result sendMessage(Message message) {


        if (message.getSendUser().isEmpty()
                ||message.getContent().isEmpty()){
            return Result.error("消息实体任意都不能为空!");
        }

        User sendUser = userService.getById(message.getSendUser());
        if (ObjectUtil.isNull(sendUser)){
            return Result.error("发送用户不存在!");
        }
        if (sendUser.getStatus()!=1){
            return Result.error("当前账户已冻结，无法发送消息!");
        }

        User receiveUser = userService.getById(message.getReceiveUser());
        if (ObjectUtil.isNotNull(receiveUser)){
//            return Result.error("接收用户不存在!");
            if (receiveUser.getStatus()!=1){
                return Result.error("当前账户已冻结，无法接收消息!");
            }
        }


        this.save(message);

        return Result.success();
    }

    @Transactional
    @Override
    public List<MessageForm> searchUserForm(String loginUserId) {
        List<MessageForm> messageFormList = new ArrayList<>();
        if (loginUserId.isEmpty()){
            return new ArrayList<>();
        }
        User loginUser = userService.getById(loginUserId);
        if (ObjectUtil.isNull(loginUser)){
            return new ArrayList<>();
        }
//        if (searchUserName.isEmpty()){
        messageFormList.addAll(findAllMessageForm(loginUser));
//        }

        return messageFormList;
    }

    @Override
    public MessageForm getBroadcastMessages(Integer userId) {
        MessageForm messageForm = new MessageForm();

        LambdaQueryWrapper<Message> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.isNull(Message::getReceiveUser).orderByAsc(Message::getCreateTime);
        List<Message> list = this.list(queryWrapper);

        messageForm.setLastMessage(!list.isEmpty()?list.getLast().getContent():"");

        messageForm.setNoReadMessageLength(list.stream().filter(s->s.getIsRead().equals(0)).toList().size());

        messageForm.setSendUser(userService.getById(userId));

        messageForm.setMessages(list);

        return messageForm;
    }


    //获取所有数据
    private List<MessageForm> findAllMessageForm(User loginUser) {
        List<MessageForm> messageFormList = new ArrayList<>();

        //获取当前websocket中的users
        Map<String, WebSocket> users = webSocketUtil.getUsers();
        Set<String> ids = users.keySet();

        messageFormList.addAll(findAllMessageChatDataWithLoginUserId(loginUser));


        // 判断ids是否在messageFormList的sendUser的Id中，不是则获取新的数据到messageFormList
        for (String id:ids){
            Long tempId = Long.parseLong(id);
            if (!messageFormList.stream().map(o->o.getSendUser().getId())
                    .toList().contains(tempId)
            ){
                MessageForm messageForm = new MessageForm();
                User sendUserData = userService.getById(tempId);
                if (ObjectUtil.isNull(sendUserData)){
                    continue;
                }

                List<Message> allMessageList = findMsgByTwoPerson(String.valueOf(loginUser.getId()),id);
                messageForm.setMessages(allMessageList);
                messageForm.setSendUser(sendUserData);
                messageForm.setReceiveUser(loginUser);
                messageForm.setIsOnline(true);
                messageForm.setNoReadMessageLength(0);
                messageForm.setLastMessage("");
                messageFormList.add(messageForm);
            }
        }


        //按照在线状态为true，有聊天记录的优先展示

        messageFormList.sort((o1, o2) -> {
            if (o1.getIsOnline() && o2.getIsOnline()) {
                return o2.getMessages().size() - o1.getMessages().size();
            } else if (o1.getIsOnline()) {
                return -1;
            } else if (o2.getIsOnline()) {
                return 1;
            } else {
                return o2.getMessages().size() - o1.getMessages().size();
            }
        });

        return messageFormList;


    }

    private List<MessageForm> findAllMessageChatDataWithLoginUserId(User loginUser) {

        Map<String, WebSocket> users = webSocketUtil.getUsers();
        Set<String> ids = users.keySet();
        List<MessageForm> messageFormList = new ArrayList<>();

        //获取所有发送消息给自己聊天的用户
        LambdaQueryWrapper<Message> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Message::getReceiveUser,String.valueOf(loginUser.getId()));
        List<String> allSendUsers = this.list(queryWrapper).stream()
                .map(Message::getSendUser).distinct().toList();
        for (String sendUser:allSendUsers){
            MessageForm messageForm = new MessageForm();
            //根据id获取当前用户实体
            User sendUserData = userService.getById(sendUser);
            if (ObjectUtil.isNull(sendUserData)){
                continue;
            }

            List<Message> sortedMessageList = findMsgByTwoPerson(sendUser, String.valueOf(loginUser.getId()));

            Message lastestMessage = sortedMessageList.stream()
                    .filter(msg->msg.getReceiveUser().equals(String.valueOf(loginUser.getId())))
                            .toList().getLast();

            //赋值最新信息
            messageForm.setLastMessage(
                    !sortedMessageList.isEmpty() ?lastestMessage
                            .getContent():""
            );

            //聊天记录
            messageForm.setMessages(sortedMessageList);
            //todo 未读信息
//            messageForm.setNoReadMessageLength(
//
//            );

            //发送人
            messageForm.setSendUser(sendUserData);
            //接收人
            messageForm.setReceiveUser(loginUser);
            //是否在线
            messageForm.setIsOnline(ids.contains(sendUser));

            messageFormList.add(messageForm);

        }

        //获取只有自己发送消息给别人的记录的用户
        LambdaQueryWrapper<Message> queryWrapper1 = new LambdaQueryWrapper<>();
        queryWrapper1.eq(Message::getSendUser,String.valueOf(loginUser.getId()));
        List<String> allSendToUsers = this.list(queryWrapper1).stream()
                .map(Message::getReceiveUser).distinct().toList();

        for (String receiveUser:allSendToUsers){
            //判断messageFormList的sendUser的userId是否包含receiveUser
            if (messageFormList.stream().anyMatch(
                    o->String.valueOf(o.getSendUser().getId()).equals(receiveUser))) {
                continue;
            }
            MessageForm messageForm = new MessageForm();
            User receiveUserData = userService.getById(receiveUser);
            if (ObjectUtil.isNull(receiveUserData)){
                continue;
            }

            messageForm.setReceiveUser(loginUser);
            messageForm.setSendUser(receiveUserData);
            messageForm.setLastMessage("");
            messageForm.setNoReadMessageLength(0);
            List<Message> sendMessageList = messageMapper.selectBySendUserAndReceiveUser(String.valueOf(loginUser.getId()), receiveUser);


            //参照createTime从小到大
            messageForm.setMessages(sendMessageList.stream()
                    .sorted(Comparator.comparing(Message::getCreateTime))
                    .toList());

            messageForm.setIsOnline(ids.contains(receiveUser));

            messageFormList.add(messageForm);

        }

        return messageFormList;
    }
}




