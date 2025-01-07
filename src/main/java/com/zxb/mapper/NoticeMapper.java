package com.zxb.mapper;

import com.zxb.entity.Notice;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author zxb
* @description 针对表【tb_notice(系统公告)】的数据库操作Mapper
* @createDate 2025-01-04 15:27:13
* @Entity com.zxb.entity.TbNotice
*/
@Mapper
public interface NoticeMapper extends BaseMapper<Notice> {

}




