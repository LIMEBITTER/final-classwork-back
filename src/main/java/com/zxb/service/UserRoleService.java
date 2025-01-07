package com.zxb.service;

import com.zxb.entity.UserRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author zxb
* @description 针对表【tb_user_role(用户角色表)】的数据库操作Service
* @createDate 2025-01-04 15:27:13
*/
public interface UserRoleService extends IService<UserRole> {

    List<Long> getRoleIds(Object loginId, String loginType);

}
