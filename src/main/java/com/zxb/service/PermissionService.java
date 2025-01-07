package com.zxb.service;

import com.zxb.common.Result;
import com.zxb.entity.Permission;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author zxb
* @description 针对表【tb_permission(菜单表)】的数据库操作Service
* @createDate 2025-01-04 15:27:13
*/
public interface PermissionService extends IService<Permission> {

    List<Permission> buildTrees(List<Permission> permissions);

    List<Permission> getRouters(Object loginId, String loginType);

    List<Long> getPermissionIds(Object loginId,String loginType);

    Result checkPermission(Permission permission);

    void deleteChildByIds(Long id);

}
