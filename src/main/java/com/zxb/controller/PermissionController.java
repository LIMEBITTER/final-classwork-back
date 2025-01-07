package com.zxb.controller;


import com.zxb.common.Result;
import com.zxb.entity.Permission;
import com.zxb.service.PermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/permission")
public class PermissionController {

    private final PermissionService permissionService;

    //构造器注入
    @Autowired
    public PermissionController(PermissionService permissionService){
        this.permissionService = permissionService;
    }

    /**
     * 新增菜单
     *
     * @param permission 菜单对象json
     * @return Result
     */
    @PostMapping
    public Result save(@RequestBody Permission permission) {
        //对传递过来的Permission数据进行校验并保存修改
        return permissionService.checkPermission(permission);
    }



    /**
     * 修改菜单值
     * @param id 当前菜单id，其实此时不需要id，因为前端父组件已经给子组件传入了id，就在Permission中
     * @param permission 一个菜单对象，若是修改，则菜单id已经通过父组件传入
     * @return Result
     */
    //todo 因为前端已经通过父组件传入permission_id，因此该id其实不需要了
    @PutMapping("/{id}")
    public Result update(@PathVariable Long id, @RequestBody Permission permission) {
        return permissionService.checkPermission(permission);
    }



    /**
     * 查询所有Permission
     *
     * @return Result
     */

    @GetMapping
    public Result findAll() {
        return Result.success(permissionService.list());
    }

    /**
     * 获取单个菜单
     *
     * @param id 菜单id
     * @return Result
     */

    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(permissionService.getById(id));
    }

    /**
     * 递归分页展示（嵌套，前端展示则是树形结构）
     *
     * @return Result
     */
    @GetMapping("/page")
    public Result findPage() {
        //按照OrderNum进行排序，查询出所有的Permissions，并通过buildTrees创建树形数据结构
        return Result.success(permissionService.buildTrees(permissionService
                .list().stream()
                .sorted(Comparator.comparing(Permission::getOrderNum, Comparator.reverseOrder())
                        .reversed())
                .collect(Collectors.toList())));
    }


    /**
     * 单个删除
     *
     * @param id 菜单id
     * @return Result
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        //删除自己，删孩子
        permissionService.removeById(id);
        permissionService.deleteChildByIds(id);
        return Result.success();
    }


}
