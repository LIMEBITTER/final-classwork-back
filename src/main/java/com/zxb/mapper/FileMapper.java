package com.zxb.mapper;

import com.zxb.entity.Files;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author zxb
* @description 针对表【tb_file(文件上传)】的数据库操作Mapper
* @createDate 2025-01-05 14:21:01
* @Entity com.zxb.entity.TbFile
*/
@Mapper
public interface FileMapper extends BaseMapper<Files> {

}




