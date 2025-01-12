package com.zxb.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zxb.entity.User;
import com.zxb.service.UserRoleService;
import com.zxb.service.UserService;
import com.zxb.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

/**
* @author zxb
* @description 针对表【tb_user(用户表)】的数据库操作Service实现
* @createDate 2025-01-04 15:27:13
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService {

    //
    private final UserRoleService userRoleService;

    @Autowired
    public UserServiceImpl(UserRoleService userRoleService){
        this.userRoleService = userRoleService;
    }

    /**
     * 通过sa-token工具类中getLoginId()，获取当前会话中的用户ID
     * 进而调用 getById() 查询数据库该id的用户信息
     * @return User
     */
    @Override
    public User getUserInfo() {

        User user = this.getById((Serializable) StpUtil.getLoginId());

        user.setRoleIds(userRoleService.getRoleIds(StpUtil.getLoginId(), "PC"));

        return user;
    }
}




