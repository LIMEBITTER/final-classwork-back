package com.zxb.mapper;

import com.zxb.entity.Order;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zxb.entity.OrderHistory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author zxb
* @description 针对表【tb_order】的数据库操作Mapper
* @createDate 2025-01-06 20:28:24
* @Entity com.zxb.entity.Order
*/
@Mapper
public interface OrderMapper extends BaseMapper<Order> {

    @Select("SELECT * FROM tb_order WHERE title LIKE CONCAT('%', #{title}, '%')")
    List<Order> selectByTitle(String title);

    @Select("SELECT * FROM tb_order_history WHERE order_id = #{orderId} ORDER BY circulation DESC LIMIT 1")
    OrderHistory selectLatestHistoryByOrderId(String orderId);


}




