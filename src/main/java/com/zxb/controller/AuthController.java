package com.zxb.controller;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpInterface;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zxb.common.Constant;
import com.zxb.common.Result;
import com.zxb.common.ResultCode;
import com.zxb.entity.User;
import com.zxb.entity.dto.LoginDto;
import com.zxb.service.PermissionService;
import com.zxb.service.UserService;
import com.zxb.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.HashMap;

@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final PermissionService permissionService;
    private final StpInterface stpInterface;
    private final RedisUtils redisUtils;

    //构造器注入
    @Autowired
    public AuthController(UserService userService,PasswordEncoder passwordEncoder,PermissionService permissionService,StpInterface stpInterface,RedisUtils redisUtils){
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.permissionService = permissionService;
        this.stpInterface = stpInterface;
        this.redisUtils = redisUtils;
    }

    /**
     *登录接口
     *
     * @param loginDto 前端传入的登录对象，json格式
     * @return SaResult
     */
    @PostMapping("/login")
    public SaResult doLogin(@RequestBody LoginDto loginDto){

        System.out.println("LoginDto"+loginDto);
        //通过用户名查询user表
        User queryUser = userService.getOne(new LambdaQueryWrapper<User>().eq(User::getUserName, loginDto.getUserName()));
        // redis中获取 验证码
        String code = String.valueOf(redisUtils.hget(Constant.CAPTCHA_PREFIX, loginDto.getToken()));
        //获取了验证码立刻删除redis中的验证码
        redisUtils.hdel(Constant.CAPTCHA_PREFIX, loginDto.getToken());
        if (!code.equals(loginDto.getCode())) {
            return SaResult.error("验证码错误！");
        }
        if (ObjectUtil.isEmpty(queryUser) || !passwordEncoder.matches(loginDto.getPassword(), queryUser.getPassword())) {
            return SaResult.error("账户或者密码错误");
        }
        if (queryUser.getStatus() == 0L) {
            return SaResult.error("账户已经被封禁，请联系系统管理员！");
        }
        //使用sa-token工具类登录，调用该方法，创建会话，将令牌信息放入tokenInfo
        StpUtil.login(queryUser.getId(),"PC");
        SaTokenInfo tokenInfo = StpUtil.getTokenInfo();

        return SaResult.data(tokenInfo);
    }

    /**
     * 获取认证信息，用于前端显示个人信息
     * @return SaResult
     */
    @GetMapping("/authInfo")
    public SaResult getPerms() {
        // 获取当前的用户信息
        // 获取用户的权限信息
        // 获取用户的角色信息
        // 获取用户的路由信息

        return SaResult.data(
                MapUtil.builder().put("rolePerms", StpUtil.getRoleList())
                        .put("permissionPerms", StpUtil.getPermissionList())
                        .put("userInfo", userService.getUserInfo())
                        .put("routers", permissionService.buildTrees(permissionService.getRouters(StpUtil.getLoginId(), "PC")))
                        .build()
        );
    }

    /**
     * 退出登录
     *
     * @return SaResult
     */
    @PostMapping("/logout")
    public SaResult logout() {
        // 使用sa-token中的工具类进行退出，干掉了服务器（会话）和客户端的token，以及redis中的token
        StpUtil.logout();
        // 这个时候就已经退出登录，前端再次发送请求就会报401的状态码
        return SaResult.ok();
    }

    /**
     * 重置密码
     *
     * @param passwordMap 原始密码，修改后密码，确认密码
     * @return
     */
    //todo 需要清除当前用户的缓存信息
    @PostMapping("/rePassword")
    //在此处就需要清除当前用户的缓存信息
    public Result repassWord(@RequestBody HashMap<String, String> passwordMap) {
        // 获取当前用户
        User user = userService.getUserInfo();
        //和原密码进行校验
        if (!passwordEncoder.matches(passwordMap.get("oldPass"), user.getPassword())) {
            return Result.error("旧密码输入错误！");
        }
        //判断前端两次输入的密码是否一致
        if (!passwordMap.get("password").equals(passwordMap.get("checkPass"))) {
            return Result.error("两次密码不一致");
        }
        //没有问题就可以设置密码
        user.setPassword(passwordEncoder.encode(passwordMap.get("password")));
        //保存返回
        return Result.success(userService.saveOrUpdate(user));
    }

    /**
     * 获取验证码
     * 用于登录页面的使用
     *
     * @return Result
     */
    @GetMapping("/captcha")
    public Result getCaptcha() {
        // 设置key
        String key = UUID.randomUUID().toString();

        //定义图形验证码的长、宽、验证码位数、干扰线数量
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(120, 40,5,80);
        //设置背景颜色
        lineCaptcha.setBackground(new Color(249, 251, 220));

        //以base64编码形式，返回给前端
        String base64String = "";
        try {
            base64String = "data:image/png;base64," + lineCaptcha.getImageBase64();

        } catch (Exception e) {
            e.printStackTrace();
        }
        /**
         * 放入redis缓存之中
         */
        redisUtils.hset(Constant.CAPTCHA_PREFIX, key, lineCaptcha.getCode(), 120);
        System.out.println("验证码："+lineCaptcha.getCode());
        return Result.success(
                MapUtil.builder()
                        .put("token", key)
                        .put("captchaImg", base64String)
                        .build()
        );

    }




}
