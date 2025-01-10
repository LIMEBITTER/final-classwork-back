package com.zxb.service;

import com.zxb.entity.Comment;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author zxb
* @description 针对表【tb_comment】的数据库操作Service
* @createDate 2025-01-10 19:47:40
*/
public interface CommentService extends IService<Comment> {

    List<Comment> findAll(String orderId);

    Object add(Comment comment);

    void delete(Integer id);

    Object findPage(Comment search, Integer pageNum, Integer pageSize);
}
