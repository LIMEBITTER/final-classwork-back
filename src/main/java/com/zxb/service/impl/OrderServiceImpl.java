package com.zxb.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zxb.entity.Order;
import com.zxb.entity.OrderHistory;
import com.zxb.entity.dto.FormDto;
import com.zxb.entity.dto.OrderAHistoryDto;
import com.zxb.service.OrderService;
import com.zxb.mapper.OrderMapper;
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

    @Autowired
    public OrderServiceImpl(OrderMapper orderMapper){
        this.orderMapper = orderMapper;
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
                final OrderAHistoryDto dto = getOrderAHistoryDto(order, history);
                //添加到list中
                combine.add(dto);
            }
        }

        //构建分页结果
        page.setRecords(combine);
        page.setTotal(combine.size());

        return page;

    }

    private static OrderAHistoryDto getOrderAHistoryDto(Order order, OrderHistory history) {
        OrderAHistoryDto dto = new OrderAHistoryDto();
        dto.setOrderId(order.getOrderId());
        dto.setTitle(order.getTitle());
        dto.setCurrentNode(history.getCurrentNode());
        dto.setOperatorName(history.getOperatorName());
        dto.setPriority(order.getPriority());
        dto.setState(order.getState());
        dto.setCreatorId(order.getCreatorId());
        dto.setCreateTime(order.getCreateTime());
        dto.setUpdateTime(order.getUpdateTime());
        return dto;
    }
}




