package com.zxb.controller;

import com.zxb.common.Result;
import com.zxb.entity.Message;
import com.zxb.entity.dto.MessageForm;
import com.zxb.mapper.MessageMapper;
import com.zxb.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.print.attribute.standard.JobMessageFromOperator;
import java.util.List;

@RestController
@RequestMapping("/message")
public class MessageController {

    private final MessageService messageService;



    @Autowired
    public MessageController(MessageService messageService){
        this.messageService = messageService;
    }

    //查找两个人的聊天记录
    @GetMapping("/findMsgByTwoPerson")
    public Result findMsgByTwoPerson(@RequestParam("sendUserId") String sendUserId,
                                     @RequestParam("receiveUserId") String receiveUserId){
        List<Message> messages = messageService.findMsgByTwoPerson(sendUserId,receiveUserId);
        return Result.success(messages);
    }

    //发送消息
    @PostMapping("/sendMessage")
    public Result sendMessage(@RequestBody Message message){


        return messageService.sendMessage(message);
    }

    //查看当前用户的信息数据
    @GetMapping("/searchUserForm")
    public Result searchUserForm(@RequestParam("loginUserId") String loginUserId){
        List<MessageForm> messages = messageService.searchUserForm(loginUserId);
        return Result.success(messages);
    }



}
