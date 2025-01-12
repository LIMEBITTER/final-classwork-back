package com.zxb.controller;

import com.zxb.common.Result;
import com.zxb.entity.Comeback;
import com.zxb.entity.User;
import com.zxb.service.ComebackService;
import com.zxb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/comeback")
public class ComebackController {

    private final ComebackService comebackService;
    private final UserService userService;

    @Autowired
    public ComebackController(ComebackService comebackService,UserService userService){
        this.comebackService = comebackService;
        this.userService = userService;
    }

    @GetMapping("/list/{orderId}")
    public Result findAll(@PathVariable String orderId){

        List<Comeback> list = comebackService.findAll(orderId);
        Long total = comebackService.getComebackTotal(orderId);
        Map<String,Object> map = new HashMap<>();
        map.put("comebackList",list);
        map.put("total",total);
        return Result.success(map);
    }
    @GetMapping("/latest")
    public Result getLatestComeback(){
        return Result.success(comebackService.getLatestComeback());
    }

    @PostMapping("/add")
    public Result addComment(@RequestBody Comeback comeback){
//        User user = userService.getById(comeback.getUserId());
//        comeback.setUserName(user.getUserName());
//        comeback.setAvatar(user.getAvatar());
//        comeback.setIsDelete(0);

        if (comebackService.save(comeback)){
            return Result.success("评论成功");
        }
        return Result.error("评论失败");
    }

    @DeleteMapping("/delete")
    public Result deleteComeback(@RequestBody Comeback comeback){
        if (comebackService.removeComeback(comeback)){
            return Result.success("删除评论成功");
        }
        return Result.error("删除评论失败");
    }

}
