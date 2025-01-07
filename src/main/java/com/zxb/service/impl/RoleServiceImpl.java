package com.zxb.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zxb.entity.Role;
import com.zxb.service.RoleService;
import com.zxb.mapper.RoleMapper;
import org.springframework.stereotype.Service;

/**
* @author zxb
* @description 针对表【tb_role(角色表)】的数据库操作Service实现
* @createDate 2025-01-04 15:27:13
*/
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role>
    implements RoleService {

}




