package com.zxb.mapper;

import com.zxb.entity.Comment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author zxb
* @description 针对表【tb_comment】的数据库操作Mapper
* @createDate 2025-01-10 19:47:40
* @Entity com.zxb.entity.Comment
*/
public interface CommentMapper extends BaseMapper<Comment> {


    @Select("select * from tb_comment where order_id = #{orderId} and parent_id = #{parentId}")
    List<Comment> findByOrderIdAndParentId(String orderId,Integer parentId);

}




