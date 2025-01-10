package com.zxb.controller;

import com.zxb.common.Result;
import com.zxb.entity.Comment;
import com.zxb.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;

    //构造器注入
    @Autowired
    public CommentController(CommentService commentService){
        this.commentService = commentService;
    }

    //查询所有数据
    @GetMapping("/{orderId}")
    public Result getComments(@PathVariable String orderId){
        return Result.success(commentService.findAll(orderId));
    }

    //新增评论
    @PostMapping
    public Result addComment(@RequestBody Comment comment){
        System.out.println(comment);
        return Result.success(commentService.save(comment));
    }

    //根据id删除
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id){
        commentService.delete(id);
        return Result.success();
    }

    //分页查询
    @PostMapping("/page")
    public Result page(@RequestBody Comment search,
                       @RequestParam(defaultValue = "1") Integer pageNum,
                       @RequestParam(defaultValue = "5") Integer pageSize){
        return Result.success(commentService.findPage(search,pageNum,pageSize));
    }
}
