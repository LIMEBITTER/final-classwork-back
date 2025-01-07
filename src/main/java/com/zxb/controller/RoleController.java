package com.zxb.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zxb.common.Result;
import com.zxb.entity.Role;
import com.zxb.entity.RolePermission;
import com.zxb.entity.UserRole;
import com.zxb.service.RolePermissionService;
import com.zxb.service.RoleService;
import com.zxb.service.UserRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/role")
public class RoleController {

    private final RoleService roleService;
    private final RolePermissionService rolePermissionService;
    private final UserRoleService userRoleService;

    //构造器注入
    @Autowired
    public RoleController(RoleService roleService,RolePermissionService rolePermissionService,UserRoleService userRoleService){
        this.roleService = roleService;
        this.rolePermissionService = rolePermissionService;
        this.userRoleService = userRoleService;
    }

    /**
     * 新增角色
     *
     * @param role 需要增加的角色对象json
     * @return Result
     */

    @PostMapping
    public Result save(@RequestBody Role role) {
        //先新增role数据
        //再根据新增的Role返回过来的ID新增RolePermission关联表数据
        roleService.save(role);
        this.saveBatchRolePermissions(role);
        return Result.success();
    }

    /**
     * 批量新增rolePermissions
     *
     * @param role 需要在role_permission关联的角色
     */
    private void saveBatchRolePermissions(Role role) {
        /*
         *   遍历role的permissionIds,
         *   每一次遍历都创建一个RolePermission对象
         *   将遍历来的pid（权限ID）和角色id存入role_permission表中进行关联
         */
        role.getPermissionIds().forEach(pid -> {
            RolePermission rolePermission = new RolePermission();
            rolePermission.setRoleId(role.getId());
            rolePermission.setPermissionId(pid);
            rolePermissionService.save(rolePermission);
        });

    }

    /**
     * 修改角色信息
     *
     * @param role 角色对象
     * @return Result
     */

    @PutMapping("/{id}")
    public Result update(@PathVariable Long id, @RequestBody Role role) {
        //先删除role_permission表中所有与该角色关联的permission_id
        rolePermissionService.remove(new LambdaQueryWrapper<RolePermission>().eq(RolePermission::getRoleId, id));
        //批量存储
        saveBatchRolePermissions(role);
        //更新role的其他信息
        return Result.success(roleService.updateById(role));
    }

    /**
     * 查询所有Role
     *
     * @return Result
     */

    @GetMapping
    public Result findAll() {
        return Result.success(roleService.list());
    }

    /**
     * 获取单个角色
     *
     * @param id 角色id
     * @return Result
     */
    //todo 不明所以的逻辑

    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        Role role = roleService.getById(id);
        role.setPermissionIds(rolePermissionService.list(new LambdaQueryWrapper<RolePermission>().eq(RolePermission::getRoleId, id)).stream().map(RolePermission::getPermissionId).collect(Collectors.toList()));
        return Result.success(role);
    }

    /**
     * 分页显示
     *
     * @param name 角色名称
     * @param pageNum 当前页，默认为1
     * @param pageSize 页码大小，默认为10条/页
     * @return Result
     */

    @GetMapping("/page")
    public Result findPage(
            @RequestParam(defaultValue = "") String name,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        return Result.success(roleService.page(new Page<>(pageNum, pageSize), new LambdaQueryWrapper<Role>().like(Role::getName, name).orderByDesc(Role::getId)));
    }

    /**
     * 单个删除
     *
     * @param id 角色id
     * @return Result
     */

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        //删role_permission关联数据
        rolePermissionService.remove(new LambdaQueryWrapper<RolePermission>().eq(RolePermission::getRoleId, id));
        //删user_role关联数据
        userRoleService.remove(new LambdaQueryWrapper<UserRole>().eq(UserRole::getRoleId, id));
        //删role数据
        return Result.success(roleService.removeById(id));
    }

    /**
     * 批量删除（事务）
     * 思路与单个删除一样
     *
     * @param ids 角色id集合
     * @return Result
     */

    @DeleteMapping("/batch/{ids}")
    @Transactional
    public Result deleteByIds(@PathVariable String[] ids) {
        Arrays.asList(ids).forEach(id -> {
            rolePermissionService.remove(new LambdaQueryWrapper<RolePermission>().eq(RolePermission::getRoleId, id));
            userRoleService.remove(new LambdaQueryWrapper<UserRole>().eq(UserRole::getRoleId, id));
        });
        return Result.success(roleService.removeByIds(Arrays.asList(ids)));
    }


}
