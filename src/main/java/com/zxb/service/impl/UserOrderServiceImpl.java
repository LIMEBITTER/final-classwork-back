package com.zxb.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zxb.entity.UserOrder;
import com.zxb.service.UserOrderService;
import com.zxb.mapper.UserOrderMapper;
import org.springframework.stereotype.Service;

/**
* @author zxb
* @description 针对表【tb_user_order】的数据库操作Service实现
* @createDate 2025-01-09 14:05:44
*/
@Service
public class UserOrderServiceImpl extends ServiceImpl<UserOrderMapper, UserOrder>
    implements UserOrderService{

}




