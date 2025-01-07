package com.zxb.mapper;

import com.zxb.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author zxb
* @description 针对表【tb_role(角色表)】的数据库操作Mapper
* @createDate 2025-01-04 15:27:13
* @Entity com.zxb.entity.TbRole
*/
@Mapper
public interface RoleMapper extends BaseMapper<Role> {

}




