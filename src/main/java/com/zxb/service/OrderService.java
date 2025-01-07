package com.zxb.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zxb.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zxb.entity.dto.FormDto;
import com.zxb.entity.dto.OrderAHistoryDto;

/**
* @author zxb
* @description 针对表【tb_order】的数据库操作Service
* @createDate 2025-01-06 20:28:24
*/
public interface OrderService extends IService<Order> {

    boolean saveOrder(FormDto formDto);

    //分页模糊查询
    IPage<OrderAHistoryDto> getOrderAHistoryPage(IPage<OrderAHistoryDto> page,String title);


}
