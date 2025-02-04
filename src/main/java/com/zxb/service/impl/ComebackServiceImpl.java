package com.zxb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zxb.entity.Comeback;
import com.zxb.entity.Role;
import com.zxb.entity.UserRole;
import com.zxb.service.ComebackService;
import com.zxb.mapper.ComebackMapper;
import com.zxb.service.RoleService;
import com.zxb.service.UserRoleService;
import com.zxb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @author zxb
* @description 针对表【tb_comeback】的数据库操作Service实现
* @createDate 2025-01-11 10:15:33
*/
@Service
public class ComebackServiceImpl extends ServiceImpl<ComebackMapper, Comeback>
    implements ComebackService{

    private final ComebackMapper comebackMapper;
    private final RoleService roleService;
    private final UserRoleService userRoleService;
    private final UserService userService;

    @Autowired
    public ComebackServiceImpl(ComebackMapper comebackMapper,RoleService roleService,UserRoleService userRoleService,UserService userService){
        this.comebackMapper = comebackMapper;
        this.roleService = roleService;
        this.userRoleService = userRoleService;
        this.userService = userService;
    }

    @Override
    public List<Comeback> findAll(String orderId) {
        LambdaQueryWrapper<Comeback> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comeback::getOrderId,orderId).orderByDesc(Comeback::getCreateTime);

        List<Comeback> list = this.list(queryWrapper);

        for (Comeback comeback:list){
            List<Long> roleIds = userRoleService.getRoleIds(comeback.getUserId(), "PC");
            comeback.setRole(roleService.listByIds(roleIds).stream().map(Role::getName).toList());
            comeback.setAvatar(userService.getById(comeback.getUserId()).getAvatar());
        }

        return processComments(list);
    }

    @Override
    public Long getComebackTotal(String orderId) {
        LambdaQueryWrapper<Comeback> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comeback::getOrderId,orderId);
        return this.count(queryWrapper);
    }

    @Override
    public List<Comeback> getLatestComeback() {
        return null;
    }

    @Override
    public boolean removeComeback(Comeback comeback) {
        return false;
    }

    private List<Comeback> processComments(List<Comeback> list){
        Map<Integer,Comeback> map = new HashMap<>();
        List<Comeback> result = new ArrayList<>();
        //所有根评论加入map
        for (Comeback comeback:list){
            //约定：parentId为null的即为父组件
            if (comeback.getParentId() == null){
                result.add(comeback);
            }
            map.put(comeback.getId(),comeback);
        }
        //子评论加入到父评论的children数组中
        for (Comeback comeback:list){
            Integer id = comeback.getParentId();
            if (id!=null){  //子评论
                Comeback p = map.get(id);
                if (p.getChildren() == null){
                    p.setChildren(new ArrayList<>());
                }
                p.getChildren().add(comeback);
            }
        }
        return result;
    }
}




