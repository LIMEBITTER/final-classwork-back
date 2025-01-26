package com.zxb.service;

import com.zxb.common.Result;
import com.zxb.entity.Message;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author zxb
* @description 针对表【tb_message】的数据库操作Service
* @createDate 2025-01-26 15:46:41
*/
public interface MessageService extends IService<Message> {

    List<Message> findMsgByTwoPerson(String sendUserId, String receiveUserId);

    Result sendMessage(Message message);
}
