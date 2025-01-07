package com.zxb.mapper;

import com.zxb.entity.UserRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author zxb
* @description 针对表【tb_user_role(用户角色表)】的数据库操作Mapper
* @createDate 2025-01-04 15:27:13
* @Entity com.zxb.entity.TbUserRole
*/
@Mapper
public interface UserRoleMapper extends BaseMapper<UserRole> {

}




