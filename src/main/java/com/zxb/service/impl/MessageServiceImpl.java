package com.zxb.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zxb.common.Result;
import com.zxb.entity.Message;
import com.zxb.entity.User;
import com.zxb.service.MessageService;
import com.zxb.mapper.MessageMapper;
import com.zxb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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

    @Autowired
    public MessageServiceImpl(UserService userService,MessageMapper messageMapper){
        this.userService = userService;
        this.messageMapper = messageMapper;
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

        final List<Message> sortedMessageList = allMessageList.stream()
                .sorted(Comparator.comparing(Message::getCreateTime))
                .toList();

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
                ||message.getReceiveUser().isEmpty()
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
        if (ObjectUtil.isNull(receiveUser)){
            return Result.error("接收用户不存在!");
        }
        if (receiveUser.getStatus()!=1){
            return Result.error("当前账户已冻结，无法接收消息!");
        }

        this.save(message);

        return Result.success();
    }
}




