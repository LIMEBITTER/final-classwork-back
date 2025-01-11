package com.zxb.service;

import com.zxb.entity.Comeback;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author zxb
* @description 针对表【tb_comeback】的数据库操作Service
* @createDate 2025-01-11 10:15:33
*/
public interface ComebackService extends IService<Comeback> {

    List<Comeback> findAll(String orderId);

    Long getComebackTotal(String orderId);

    List<Comeback> getLatestComeback();

    boolean removeComeback(Comeback comeback);
}
