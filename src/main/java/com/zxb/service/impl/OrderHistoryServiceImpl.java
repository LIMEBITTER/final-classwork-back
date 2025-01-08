package com.zxb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zxb.entity.OrderHistory;
import com.zxb.entity.User;
import com.zxb.entity.dto.FormDto;
import com.zxb.service.OrderHistoryService;
import com.zxb.mapper.OrderHistoryMapper;
import com.zxb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author zxb
* @description 针对表【tb_order_history】的数据库操作Service实现
* @createDate 2025-01-06 19:45:48
*/
@Service
public class OrderHistoryServiceImpl extends ServiceImpl<OrderHistoryMapper, OrderHistory>
    implements OrderHistoryService{

    private final UserService userService;

    @Autowired
    public OrderHistoryServiceImpl(UserService userService){
        this.userService = userService;
    }

    @Override
    public boolean saveHistory(FormDto formDto) {
        OrderHistory history = new OrderHistory();
        history.setOrderId(formDto.getOrder_id());
        history.setOperatorId(formDto.getRelated_person());

        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(User::getUserName).eq(User::getId,formDto.getRelated_person());
        User user = userService.getOne(queryWrapper);

        history.setOperatorName(user.getUserName());
        //默认转交状态为同意
        history.setCirculation(1);
        history.setCurrentNode("提交工单");
        return this.save(history);
    }
}




