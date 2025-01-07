package com.zxb.service.impl;

import cn.hutool.core.util.ObjUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zxb.common.Result;
import com.zxb.entity.Permission;
import com.zxb.entity.Role;
import com.zxb.entity.RolePermission;
import com.zxb.entity.dto.Meta;
import com.zxb.service.PermissionService;
import com.zxb.mapper.PermissionMapper;
import com.zxb.service.RolePermissionService;
import com.zxb.service.RoleService;
import com.zxb.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author zxb
* @description 针对表【tb_permission(菜单表)】的数据库操作Service实现
* @createDate 2025-01-04 15:27:13
*/
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission>
    implements PermissionService {

    private final RolePermissionService rolePermissionService;
    private final UserRoleService userRoleService;
    private final RoleService roleService;

    //构造器注入
    @Autowired
    public PermissionServiceImpl(RoleService roleService,UserRoleService userRoleService,RolePermissionService rolePermissionService){
        this.roleService = roleService;
        this.userRoleService = userRoleService;
        this.rolePermissionService = rolePermissionService;
    }

    /**
     * 双重for循环构建树形结构
     * 返回的是 菜单表中所有的菜单
     *
     * @param permissions 菜单集合
     * @return List<Permission>
     */
    @Override
    public List<Permission> buildTrees(List<Permission> permissions) {
        //创建一个空的Permission集合
        List<Permission> finallyPermissions = new ArrayList<>();
        //进行第一层for循环
        for (Permission firstPermission : permissions) {
            //进行第二层for循环
            for (Permission secondPermission : permissions) {
                //当第二层循环的permission的ParentId等于第一层的Id时，将第二层的Permission添加到第一层Permission的Children集合属性里面
                //子菜单
                if (secondPermission.getParentId().equals(firstPermission.getId())) {
                    firstPermission.getChildren().add(secondPermission);
                }

            }
            //如果第一层的ParentId=0，也就是说第一层上面没有父级的时候，将这个permission添加到初始创建的那个permission集合中
            //兄弟菜单
            if (firstPermission.getParentId() == 0L) {
                finallyPermissions.add(firstPermission);
            }
        }

        //返回finallyPermissions
        return finallyPermissions;
    }

    /**
     * 获取用户的路由和菜单信息
     * 返回的是当前用户Id所拥有的菜单列表
     *
     * @param loginId 用户id
     * @param loginType 多用户鉴权使用，本项目中该参数基本不用
     * @return List<Permission>
     */
    @Override
    public List<Permission> getRouters(Object loginId, String loginType) {

        //调用方法
        List<Long> permissionIds = getPermissionIds(loginId, loginType);

        //权限id长度为0，当前角色未被分配权限
        if (permissionIds.isEmpty()) {
            return new ArrayList<>();
        }
        /*
         * 通过权限id集合查询出所有当前角色所拥有的菜单项
         * 通过stream过滤出当前菜单未被禁用，（隐藏状态不会被过滤），
         * 且菜单类型menuType为目录（1）或者菜单（2）的所有菜单
         */
        List<Permission> routersByDataSource = this.listByIds(permissionIds)
                .stream()
                .filter(permission ->
                permission.getStatus() == 1L
                        && (permission.getStatus() == 1 && (permission.getMenuType() == 1 || permission.getMenuType() == 2)))
                .distinct()
                .sorted(Comparator.comparing(Permission::getOrderNum, Comparator.reverseOrder())
                        .reversed())
                .collect(Collectors.toList());

        /*
         * 将路由集合进行一下转换，转化为前端需要的方式，
         * 也就是将部分属性，加入到meta对象属性中去，
         * 这样子再前端就可以直接使用不用再次转化了
         *
         */
        routersByDataSource
                .forEach(r -> r.setMeta(new Meta(r.getIcon(), r.getTitle(), r.getHidden())));

        return routersByDataSource;
    }

    //该类为获取指定userId所拥有的权限id集合
    @Override
    public List<Long> getPermissionIds(Object loginId,String loginType){
        //获取该userId下的角色列表的id
        List<Long> roleIds = userRoleService.getRoleIds(loginId, loginType);
        if (roleIds.isEmpty()){
            return new ArrayList<>();
        }
        //获取当前userId下对应未被封禁的角色
        List<Role> roles = roleService.list(new LambdaQueryWrapper<Role>().in(Role::getId, roleIds).eq(Role::getStatus, 1L));
        if (roles.isEmpty()) {
            return new ArrayList<>();
        }
        //创造查询条件，查询在role_permission表中 符合的roleId
        LambdaQueryWrapper<RolePermission> rolePermissionLambdaQueryWrapper = new LambdaQueryWrapper<RolePermission>().in(RolePermission::getRoleId, roleIds);

        // 根据上方符合的roleId集合，通过stream过滤出只有 permissionId的集合

        return rolePermissionService.list(rolePermissionLambdaQueryWrapper).stream().map(RolePermission::getPermissionId).collect(Collectors.toList());
    }

    /**
     * 校验传递的权限数据的合法性
     *
     * @param permission 菜单对象
     * @return Result
     */
    @Override
    public Result checkPermission(Permission permission) {
        // 先找出来父级菜单
        Permission parentPermission = this.getById(permission.getParentId());
        //判断是否为按钮菜单类型且在根目录下面
        if (permission.getParentId().equals(0L) && permission.getMenuType() == 3) {
            return Result.error("按钮不能在根目录下面！");
        }
        //判断是否为修改，若是，则要再次判断父级目录是否为自己
        if (ObjUtil.isNotEmpty(permission.getId())) {
            if (permission.getParentId().equals(permission.getId())) {
                return Result.error("父级目录不能是自己!");
            }
        }
        //判断一下父级菜单类型是否为按钮，正常来说，父级菜单类型只能是目录或者是菜单
        if (parentPermission != null) {
            if (parentPermission.getMenuType() == 3) {
                return Result.error("父级目录不能是按钮!");
            }

        }
        //进行保存（新增或者的修改）
        return Result.success(this.saveOrUpdate(permission));
    }

    /**
     * 递归删除自己和子级菜单
     *
     * @param id 菜单id
     */
    @Override
    public void deleteChildByIds(Long id) {
        //删除role_permission表的关联项
        rolePermissionService.remove(new LambdaQueryWrapper<RolePermission>().eq(RolePermission::getPermissionId, id));
        //创造出找出子级的查询条件
        LambdaQueryWrapper<Permission> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Permission::getParentId, id);
        //过滤出子级ids
        List<Long> ids = this.list(lambdaQueryWrapper).stream().map(Permission::getId).collect(Collectors.toList());
        //批量删除删除子级
        this.removeByIds(ids);
        //查询子级是否有子级，如果存在子级，进行递归调用删除子级。
        for (Long pid : ids) {
            List<Permission> permissions = this.list(new LambdaQueryWrapper<Permission>().eq(Permission::getParentId, pid));
            if (!permissions.isEmpty()) {
                this.deleteChildByIds(pid);
            }
        }
    }
}




