package com.zxb.mapper;

import com.zxb.entity.UserOrder;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author zxb
* @description 针对表【tb_user_order】的数据库操作Mapper
* @createDate 2025-01-09 14:05:44
* @Entity com.zxb.entity.UserOrder
*/
@Mapper
public interface UserOrderMapper extends BaseMapper<UserOrder> {

}




