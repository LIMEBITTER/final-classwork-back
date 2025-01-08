package com.zxb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zxb.entity.Order;
import com.zxb.entity.OrderHistory;
import com.zxb.entity.User;
import com.zxb.entity.dto.FormDto;
import com.zxb.entity.dto.OrderAHistoryDto;
import com.zxb.service.OrderService;
import com.zxb.mapper.OrderMapper;
import com.zxb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
* @author zxb
* @description 针对表【tb_order】的数据库操作Service实现
* @createDate 2025-01-06 20:28:24
*/
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order>
    implements OrderService{

    private final OrderMapper orderMapper;
    private final UserService userService;

    @Autowired
    public OrderServiceImpl(OrderMapper orderMapper,UserService userService){
        this.orderMapper = orderMapper;
        this.userService = userService;
    }

    @Override
    public boolean saveOrder(FormDto formDto) {
        Order order = new Order();
        order.setTitle(formDto.getTitle());
        order.setCreatorId(formDto.getCreator_id());
        order.setOrderId(formDto.getOrder_id());
        order.setPriority(formDto.getPriority());
        order.setRelatedPerson(formDto.getRelated_person());
        order.setComplaint(formDto.getComplaint());
        order.setState(1);
        return this.save(order);
    }

    @Override
    public IPage<OrderAHistoryDto> getOrderAHistoryPage(IPage<OrderAHistoryDto> page, String title) {
        //根据title模糊查询
        List<Order> orders = orderMapper.selectByTitle(title);
        List<OrderAHistoryDto> combine = new ArrayList<>();

        for (Order order:orders){
            OrderHistory history = orderMapper.selectLatestHistoryByOrderId(order.getOrderId());
            if (history!=null){
                OrderAHistoryDto dto = this.getOrderAHistoryDto(order, history);
                //添加到list中
                combine.add(dto);
            }
        }


        //根据优先级降序
        combine.sort((dto1,dto2)-> dto2.getPriority().compareTo(dto1.getPriority()));

        //构建分页结果
        page.setRecords(combine);
        page.setTotal(combine.size());


        return page;

    }

    private OrderAHistoryDto getOrderAHistoryDto(Order order, OrderHistory history) {
        OrderAHistoryDto dto = new OrderAHistoryDto();
        dto.setOrderId(order.getOrderId());
        dto.setTitle(order.getTitle());
        dto.setCurrentNode(history.getCurrentNode());
        dto.setOperatorName(history.getOperatorName());
        dto.setPriority(order.getPriority());
        dto.setState(order.getState());


        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(User::getUserName).eq(User::getId,order.getCreatorId());
        User user = userService.getOne(queryWrapper);

        dto.setCreatorName(user.getUserName());
        dto.setCreateTime(order.getCreateTime());
        dto.setUpdateTime(order.getUpdateTime());
        return dto;
    }
}




