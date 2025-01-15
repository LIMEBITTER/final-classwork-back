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
import java.util.List;

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
                new LambdaQueryWrapper<Notice>().like(Notice::getTitle,title).orderByDesc(Notice::getLevel)));
    }

    //todo 1.12需要用dto，未完成
    @PostMapping("/save")
    @Transactional
    public Result save(@RequestBody Notice notice){

        List<Integer> targetRoleIds = notice.getTargetRoleIds();
        noticeService.save(notice);
        Integer noticeId = notice.getId();

        for (Integer role_id:targetRoleIds){
            NoticeRole noticeRole = new NoticeRole();
            noticeRole.setNoticeId(noticeId);
            noticeRole.setRoleId(role_id);
            noticeRoleService.save(noticeRole);
        }


        return Result.success();
    }

    @PutMapping("/{id}")
    @Transactional
    public Result update(@PathVariable Integer id, @RequestBody Notice notice){
        List<Integer> targetRoleIds = notice.getTargetRoleIds();
        noticeRoleService.remove(new LambdaQueryWrapper<NoticeRole>().eq(NoticeRole::getNoticeId,id));
        for (Integer role_id:targetRoleIds){
            NoticeRole noticeRole = new NoticeRole();
            noticeRole.setNoticeId(id);
            noticeRole.setRoleId(role_id);
            noticeRoleService.save(noticeRole);
        }

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

    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id){
        Notice notice = noticeService.getById(id);
        System.out.println(notice);
        List<Integer> roleIds = noticeRoleService
                .list(new LambdaQueryWrapper<NoticeRole>().eq(NoticeRole::getNoticeId, id))
                .stream().map(NoticeRole::getRoleId).toList();
        notice.setTargetRoleIds(roleIds);
        return Result.success(notice);
    }

    @PostMapping("/publish")
    public Result publish(@RequestParam("id") Integer id,
                          @RequestParam("status") Integer status){
        Notice notice = noticeService.getById(id);
        notice.setPublishStatus(status);
        return Result.success(noticeService.updateById(notice));

    }

    //通过roleIds查询公告
    @PostMapping("/findNoticeByRole")
    public Result findNoticeByRole(@RequestBody List<Integer> ids){

        List<Integer> noticeIds = noticeRoleService
                .list(new LambdaQueryWrapper<NoticeRole>().in(NoticeRole::getRoleId, ids))
                .stream().map(NoticeRole::getNoticeId).toList();

        List<Notice> list = noticeService.list(new LambdaQueryWrapper<Notice>().in(Notice::getId, noticeIds).ne(Notice::getPublishStatus, 0));


        return Result.success(list);
    }









}
