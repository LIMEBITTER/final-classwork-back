package com.zxb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zxb.entity.UserRole;
import com.zxb.service.UserRoleService;
import com.zxb.mapper.UserRoleMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
* @author zxb
* @description 针对表【tb_user_role(用户角色表)】的数据库操作Service实现
* @createDate 2025-01-04 15:27:13
*/
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole>
    implements UserRoleService {

    /**
     * 通过用户ID获取该用户所拥有的角色集合
     *
     * @param loginId
     * @param loginType 该参数一般在多用户鉴权时使用（比如将管理员与普通用户单独成表），当前项目基本用不到
     * @return
     */
    @Override
    public List<Long> getRoleIds(Object loginId, String loginType) {
        LambdaQueryWrapper<UserRole> userRoleLambdaQueryWrapper = new LambdaQueryWrapper<UserRole>().in(UserRole::getUserId, loginId);

        //过滤出不重复的角色Id
        return this.list(userRoleLambdaQueryWrapper).stream().map(UserRole::getRoleId).collect(Collectors.toList());
    }



}




