package com.zxb.service.impl;

import cn.dev33.satoken.stp.StpInterface;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zxb.entity.Permission;
import com.zxb.entity.Role;
import com.zxb.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 自定义权限加载接口实现类，（sa-token自定义权限认证器，该类配置完即自动加载）
 */
@Component
public class StpInterfaceImpl implements StpInterface {

    private final UserRoleService userRoleService;
    private final UserService userService;
    private final RoleService roleService;
    private final PermissionService permissionService;
    private final RolePermissionService rolePermissionService;

    //构造器注入
    @Autowired
    public StpInterfaceImpl(UserRoleService userRoleService,UserService userService,RoleService roleService,PermissionService permissionService,RolePermissionService rolePermissionService){
        this.userService = userService;
        this.userRoleService = userRoleService;
        this.roleService = roleService;
        this.permissionService = permissionService;
        this.rolePermissionService = rolePermissionService;
    }


    /**
     * 返回一个账号所拥有的权限码集合  e.g.sys:log:add
     * 前端对按钮这些组件的权限控制就是通过权限码判断的
     *
     * @param loginId
     * @param loginType
     * @return
     */
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {

        //调用方法
        List<Long> permissionIds = permissionService.getPermissionIds(loginId, loginType);

        //未被分配菜单
        if (permissionIds.size() == 0) {
            return new ArrayList<>();
        }

        //通过当前用户所拥有的权限id集合，过滤出权限码集合
        List<String> permissionPermslist = permissionService
                .listByIds(permissionIds)
                .stream()
                .filter(permission -> permission.getStatus() == 1L)
                .map(Permission::getPerms).collect(Collectors.toList());


        return permissionPermslist;
    }

    /**
     * 返回一个账号所拥有的角色唯一标识集合  e.g. admin   user
     *
     * @param loginId
     * @param loginType
     * @return
     */
    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        List<Long> userRoleIds = userRoleService.getRoleIds(loginId, loginType);
        if (userRoleIds.size() == 0) {
            return new ArrayList<>();
        }
        List<String> rolePermsList =
                roleService.list(new LambdaQueryWrapper<Role>()
                                .in(Role::getId, userRoleIds)
                                .eq(Role::getStatus, 1L))
                        .stream().map(Role::getPerms)
                        .collect(Collectors.toList());

        return rolePermsList;
    }
}
