package com.zxb.service;

import com.zxb.entity.Detail;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zxb.entity.dto.FormDto;

/**
* @author zxb
* @description 针对表【tb_detail】的数据库操作Service
* @createDate 2025-01-06 20:28:31
*/
public interface DetailService extends IService<Detail> {

    boolean saveDetail(FormDto formDto);
}
