package com.zxb.mapper;

import com.zxb.entity.Notice;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author zxb
* @description 针对表【tb_notice(系统公告)】的数据库操作Mapper
* @createDate 2025-01-12 15:39:43
* @Entity com.zxb.entity.Notice
*/
@Mapper
public interface NoticeMapper extends BaseMapper<Notice> {

}




