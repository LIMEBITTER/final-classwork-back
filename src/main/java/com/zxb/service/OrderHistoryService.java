package com.zxb.service;

import com.zxb.entity.OrderHistory;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zxb.entity.dto.FormDto;

/**
* @author zxb
* @description 针对表【tb_order_history】的数据库操作Service
* @createDate 2025-01-06 19:45:48
*/
public interface OrderHistoryService extends IService<OrderHistory> {

    boolean saveHistory(FormDto formDto);
}
