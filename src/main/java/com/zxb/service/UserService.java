package com.zxb.service;

import com.zxb.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author zxb
* @description 针对表【tb_user(用户表)】的数据库操作Service
* @createDate 2025-01-04 15:27:13
*/
public interface UserService extends IService<User> {

    User getUserInfo();

}
