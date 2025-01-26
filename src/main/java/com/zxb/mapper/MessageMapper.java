package com.zxb.mapper;

import com.zxb.entity.Message;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author zxb
* @description 针对表【tb_message】的数据库操作Mapper
* @createDate 2025-01-26 15:46:41
* @Entity com.zxb.entity.Message
*/
public interface MessageMapper extends BaseMapper<Message> {

    @Select("select * from tb_message where send_user = #{sendUserId} and receive_user = #{receiveUserId}")
    List<Message> selectBySendUserAndReceiveUser(String sendUserId,String receiveUserId);

}




