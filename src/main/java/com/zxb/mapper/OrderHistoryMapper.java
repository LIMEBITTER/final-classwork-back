package com.zxb.mapper;

import com.zxb.entity.OrderHistory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author zxb
* @description 针对表【tb_order_history】的数据库操作Mapper
* @createDate 2025-01-06 19:45:48
* @Entity com.zxb.entity.OrderHistory
*/
@Mapper
public interface OrderHistoryMapper extends BaseMapper<OrderHistory> {

}




