package com.zxb.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zxb.common.Constant;
import com.zxb.common.Result;
import com.zxb.entity.User;
import com.zxb.entity.UserRole;
import com.zxb.service.UserRoleService;
import com.zxb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;


@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final UserRoleService userRoleService;

    //构造器注入
    @Autowired
    public UserController(UserService userService,PasswordEncoder passwordEncoder,UserRoleService userRoleService){
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.userRoleService = userRoleService;
    }

    /**
     * 新增
     *
     * @param user 用户新增对象json
     * @return Result
     */

    @PostMapping
    public Result save(@RequestBody User user) {
        System.out.println("user"+user);
        if (userService.exists(new LambdaQueryWrapper<User>().eq(User::getUserName, user.getUserName()))) {
            return Result.error("此账户名已经存在，请更换！");
        }
        //设置默认密码 123456
        user.setPassword(passwordEncoder.encode(Constant.DEFAULT_PASSWORD));
        System.out.println(user);
        userService.save(user);
        //将创建时选择的角色id存入user_role表中
        this.saveBatchUserRoles(user);

        return Result.success();
    }

    /**
     * 批量新增userRole
     *
     * @param user 用户新增对象，主要遍历提交的roleId
     */
    private void saveBatchUserRoles(User user) {
        user.getRoleIds().forEach(rid -> {
            UserRole userRole = new UserRole();
            userRole.setUserId(user.getId());
            userRole.setRoleId(rid);
            userRoleService.save(userRole);
        });
    }

    /**
     * 修改
     *
     * @param user 有id的user对象，即为修改
     * @return Result
     */

    @PutMapping("/{id}")
    public Result update(@PathVariable Long id, @RequestBody User user) {
        //先删除掉此用户所有的roleIds，然后再添加前端传递过来的数据
        userRoleService.remove(new LambdaQueryWrapper<UserRole>().eq(UserRole::getUserId, id));
        this.saveBatchUserRoles(user);
        //更新用户其他信息
        return Result.success(userService.updateById(user));
    }
    /**
     * 修改个人信息
     *
     * @param user 需要修改的user对象json
     * @return Result
     */
    @PutMapping
    public Result updateInformation(@RequestBody User user) { return Result.success(userService.updateById(user)); }

    /**
     * 查询所有User
     *
     * @return Result
     */
    @GetMapping
    public Result findAll() {
        return Result.success(userService.list());
    }

    /**
     * 获取单个User对象
     *
     * @param id 需要查询的userId
     * @return Result
     */
    //todo 不明所以的逻辑，再看看

    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        User user = userService.getById(id);
        user.setRoleIds(userRoleService.getRoleIds(id,"PC"));
        return Result.success(user);
    }

    /**
     * 分页显示（以及用户名模糊查询）
     *
     * @param name 要模糊查询的用户名
     * @param pageNum 页码，默认为第一页
     * @param pageSize 每页展示的数据量，默认为10条/页
     * @return Result
     */

    @GetMapping("/page")
    public Result findPage(
            @RequestParam(defaultValue = "") String name,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {

        return Result.success(userService.page(new Page<>(pageNum, pageSize),
                new LambdaQueryWrapper<User>().like(User::getUserName, name)
                        .orderByDesc(User::getId)));
    }

    /**
     * 根据用户Id删除单个用户
     *
     * @param id 需要删除的用户id
     * @return Result
     */

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        //user_role以及user表相继删除
        removeUserAndLogout(id);
        return Result.success(userService.removeById(id));
    }

    /**
     * 删除和下线用户
     *
     * @param id 需要删除的用户id
     */
    private void removeUserAndLogout(Object id) {
        userRoleService.remove(new LambdaQueryWrapper<UserRole>().eq(UserRole::getUserId, id));
        StpUtil.logout(id, "PC");
    }

    /**
     * 批量删除（事务注解）
     *
     * @param ids 需要删除的用户id集合
     * @return Result
     */

    @DeleteMapping("/batch/{ids}")
    @Transactional
    public Result deleteByIds(@PathVariable String[] ids) {
        //遍历，每个用户id都执行删除和下线操作（使用lambda表达式）
        Arrays.asList(ids).forEach(this::removeUserAndLogout);
        return Result.success(userService.removeByIds(Arrays.asList(ids)));
    }


}
