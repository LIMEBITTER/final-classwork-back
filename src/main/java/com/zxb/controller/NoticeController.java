package com.zxb.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zxb.common.Result;
import com.zxb.entity.Notice;
import com.zxb.entity.NoticeRole;
import com.zxb.entity.Role;
import com.zxb.service.NoticeRoleService;
import com.zxb.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequestMapping("/notice")
public class NoticeController {


    private final NoticeService noticeService;
    private final NoticeRoleService noticeRoleService;

    @Autowired
    public NoticeController(NoticeService noticeService,NoticeRoleService noticeRoleService){
        this.noticeService = noticeService;
        this.noticeRoleService = noticeRoleService;
    }

    /**
     * 分页展示
     * @param title
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/page")
    public Result findPage(@RequestParam(defaultValue = "") String title,
                           @RequestParam(defaultValue = "1") Integer pageNum,
                           @RequestParam(defaultValue = "10") Integer pageSize){
        return Result.success(noticeService.page(new Page<>(pageNum,pageSize),
                new LambdaQueryWrapper<Notice>().like(Notice::getTitle,title).orderByDesc(Notice::getId)));
    }

    //todo 1.12需要用dto，未完成
    @PostMapping("/save")
    public Result save(@RequestBody Notice notice){
        noticeService.save(notice);

        return Result.success();
    }

    @PutMapping("/{id}")
    public Result update(@PathVariable Integer id, @RequestBody Notice notice){
        noticeRoleService.remove(new LambdaQueryWrapper<NoticeRole>().eq(NoticeRole::getNoticeId,id));

        return Result.success(noticeService.updateById(notice));
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id){
        noticeRoleService.remove(new LambdaQueryWrapper<NoticeRole>().eq(NoticeRole::getNoticeId,id));
        return Result.success(noticeService.removeById(id));

    }

    @DeleteMapping("/batch/{ids}")
    @Transactional
    public Result deleteByIds(@PathVariable String[] ids){
        Arrays.asList(ids).forEach(id -> {
            noticeRoleService.remove(new LambdaQueryWrapper<NoticeRole>().eq(NoticeRole::getNoticeId,id));
        });
        return Result.success(noticeService.removeByIds(Arrays.asList(ids)));
    }









}
