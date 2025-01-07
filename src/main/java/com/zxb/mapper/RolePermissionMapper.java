package com.zxb.mapper;

import com.zxb.entity.RolePermission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author zxb
* @description 针对表【tb_role_permission(角色菜单表)】的数据库操作Mapper
* @createDate 2025-01-04 15:27:13
* @Entity com.zxb.entity.TbRolePermission
*/
@Mapper
public interface RolePermissionMapper extends BaseMapper<RolePermission> {

}




