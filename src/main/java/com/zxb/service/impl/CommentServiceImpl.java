package com.zxb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zxb.entity.Comment;
import com.zxb.entity.User;
import com.zxb.service.CommentService;
import com.zxb.mapper.CommentMapper;
import com.zxb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author zxb
* @description 针对表【tb_comment】的数据库操作Service实现
* @createDate 2025-01-10 19:47:40
*/
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment>
    implements CommentService{

    private final CommentMapper commentMapper;
    private final UserService userService;

    @Autowired
    public CommentServiceImpl(CommentMapper commentMapper,UserService userService){
        this.commentMapper = commentMapper;
        this.userService = userService;
    }

    @Override
    public List<Comment> findAll(String orderId) {


        List<Comment> list = commentMapper.findByOrderIdAndParentId(orderId,0);
        for (Comment comment:list){
            setData(comment);

            List<Comment> children = commentMapper.findByOrderIdAndParentId(orderId,comment.getId());
            if (!children.isEmpty()){
                for (Comment child:children){
                    setData(child);
                }

                comment.setChildren(children);
            }

        }

        return list;
    }

    private void setData(Comment comment){
        User user = userService.getById(comment.getUserId());
        comment.setUserName(user.getUserName());
        comment.setAvatar(user.getAvatar());
    }

    @Override
    public Object add(Comment comment) {
        return null;
    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public Object findPage(Comment search, Integer pageNum, Integer pageSize) {
        return null;
    }
}




