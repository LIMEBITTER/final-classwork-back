package com.zxb.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zxb.entity.Detail;
import com.zxb.entity.dto.FormDto;
import com.zxb.service.DetailService;
import com.zxb.mapper.DetailMapper;
import org.springframework.stereotype.Service;

/**
* @author zxb
* @description 针对表【tb_detail】的数据库操作Service实现
* @createDate 2025-01-06 20:28:31
*/
@Service
public class DetailServiceImpl extends ServiceImpl<DetailMapper, Detail>
    implements DetailService{

    @Override
    public boolean saveDetail(FormDto formDto) {
        Detail detail = new Detail();
        detail.setOrderId(formDto.getOrder_id());
        return this.save(detail);
    }
}




