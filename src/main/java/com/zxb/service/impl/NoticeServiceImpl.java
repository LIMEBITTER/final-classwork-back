package com.zxb.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zxb.entity.Notice;
import com.zxb.service.NoticeService;
import com.zxb.mapper.NoticeMapper;
import org.springframework.stereotype.Service;

/**
* @author zxb
* @description 针对表【tb_notice(系统公告)】的数据库操作Service实现
* @createDate 2025-01-04 15:27:13
*/
@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice>
    implements NoticeService {

}




