package com.zxb.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zxb.entity.Files;
import com.zxb.service.FileService;
import com.zxb.mapper.FileMapper;
import org.springframework.stereotype.Service;

/**
* @author zxb
* @description 针对表【tb_file(文件上传)】的数据库操作Service实现
* @createDate 2025-01-05 14:21:01
*/
@Service
public class FileServiceImpl extends ServiceImpl<FileMapper, Files>
    implements FileService {

}




