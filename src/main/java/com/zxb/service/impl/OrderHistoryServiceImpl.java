package com.zxb.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zxb.entity.OrderHistory;
import com.zxb.entity.dto.FormDto;
import com.zxb.service.OrderHistoryService;
import com.zxb.mapper.OrderHistoryMapper;
import org.springframework.stereotype.Service;

/**
* @author zxb
* @description 针对表【tb_order_history】的数据库操作Service实现
* @createDate 2025-01-06 19:45:48
*/
@Service
public class OrderHistoryServiceImpl extends ServiceImpl<OrderHistoryMapper, OrderHistory>
    implements OrderHistoryService{

    @Override
    public boolean saveHistory(FormDto formDto) {
        OrderHistory history = new OrderHistory();
        history.setOrderId(formDto.getOrder_id());
        history.setOperatorId(formDto.getRelated_person());
        history.setOperatorName(formDto.getUsername());
        //默认转交状态为同意
        history.setCirculation(1);
        history.setCurrentNode("提交工单");
        return this.save(history);
    }
}




