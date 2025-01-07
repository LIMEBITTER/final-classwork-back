package com.zxb.controller;


import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zxb.common.Constant;
import com.zxb.common.Result;
import com.zxb.entity.Files;
import com.zxb.entity.User;
import com.zxb.service.FileService;
import com.zxb.service.UserService;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;


@RestController
@RequestMapping("/file")
public class FileController {

    //将yml配置文件的文件位置赋予给该变量
    @Value("${files.upload.path}")
    private String fileUploadPath;

    private final FileService fileService;
    private final UserService userService;

    //构造器注入
    @Autowired
    public FileController(FileService fileService,UserService userService){
        this.fileService = fileService;
        this.userService = userService;
    }

    /**
     * 上传文件
     *
     * @param file 文件对象
     * @param id 用户id
     * @return Result
     */
    @PostMapping("/upload/{id}")
    public Result upload(@RequestParam MultipartFile file,
                         @PathVariable("id") Integer id) throws IOException {

        System.out.println("@@@@"+id);
        String originalFilename = file.getOriginalFilename(); //获取( 原始名称 )
        String type = FileUtil.extName(originalFilename); //获取( 文件类型 )    //注意FileUtil.extName是String
        long size = file.getSize(); //获取( 文件大小 )

        // 1.定义一个文件唯一的标识位
        String uuid = IdUtil.fastSimpleUUID();
        String fileUUID = uuid + StrUtil.DOT + type;
        File uploadFile = new File(fileUploadPath + fileUUID); //StrUtil.DOT( 文件名) + type( png )

        // 2.判断配置的文件目录是否存在，若不存在则创建一个新的文件目录
        if (!uploadFile.getParentFile().exists()) {
            uploadFile.getParentFile().mkdirs();
        }






        // 3.获取文件的url
        String url;
        // 上传文件到磁盘
        file.transferTo(uploadFile);
        // 获取文件的md5
        String md5 = SecureUtil.md5(uploadFile);
        // 从数据库查询是否存在相同的记录
        Files dbFiles = getFileByMd5(md5);
        if (dbFiles != null) {
            url = dbFiles.getUrl();
            // 由于文件已存在，所以删除刚才上传的重复文件
            uploadFile.delete();
        } else {
            // 数据库若不存在重复文件,则不删除刚才上传的文件
            url = Constant.FILE_UPLOAD_ADDRESS + fileUUID;
        }

        //id非法，则不执行更新用户数据库
        if (id != -1 && id != 0){
            //通过userId查询出对应的User对象，并设置新的头像url，并更新至数据库
            User updateUser = userService.getById(id);
            updateUser.setAvatar(url);
            userService.updateById(updateUser);
        }




        // 4.再存储到数据库
        Files saveFile = new Files();
        saveFile.setName(originalFilename);
        saveFile.setType(type);
        saveFile.setSize(size / 1024);//转换在数据库显示的图片大小为KB
        saveFile.setUrl(url);
        saveFile.setMd5(md5);
        fileService.save(saveFile);
        return Result.success(url);  //上传成功后返回url
    }







    /**
     * 文件下载
     *
     * @param fileUUID 文件唯一标识位
     * @param response 响应头
     */
    @GetMapping("/{fileUUID}")
    public void download(@PathVariable String fileUUID, HttpServletResponse response) throws IOException {
        // 根据文件的唯一标识码获取文件
        File uploadFile = new File(fileUploadPath + fileUUID);

        // 设置输出流格式
        ServletOutputStream os = response.getOutputStream(); // 写出流
        response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileUUID, StandardCharsets.UTF_8));
        response.setContentType("application/octet-stream");

        // 读取文件的字节流
        os.write(FileUtil.readBytes(uploadFile));
        os.flush();
        os.close();
    }

    /**
     * 通过md5查询文件，只查询 1个
     *
     * @param md5 要查询的md5
     * @return Files
     */
    //todo 原文件令人疑惑，service都暴露了，为什么要直接操纵mapper
    private Files getFileByMd5(String md5) {
        // 查询文件的md5是否存在
        List<Files> filesList = fileService.list(new LambdaQueryWrapper<Files>().eq(Files::getMd5, md5));
        return filesList.isEmpty() ? null : filesList.getFirst();
    }

    /**
     * 分页显示（只查询is_delete状态为0的文件列表）
     *
     * @param pageNum 当前页
     * @param pageSize 页码
     * @param name 模糊查询的文件名
     * @return Result
     */

    @GetMapping("/page")

    public Result findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                           @RequestParam(defaultValue = "10") Integer pageSize,
                           @RequestParam(defaultValue = "") String name) {
        Page<Files> page = fileService.page(new Page<>(pageNum, pageSize),
                new LambdaQueryWrapper<Files>().eq(Files::getIsDelete, false)
                        .like(Files::getName, name).orderByDesc(Files::getId));


        return Result.success(page);

    }





    //todo 注意，下面两个删除文件的方法仅仅是将file表中is_delete字段置为了1，并没有真正的删除！
    /**
     * 删除文件
     *
     * @param id 要删除文件的id
     * @return Result
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        Files files = fileService.getById(id);
        files.setIsDelete(true);
        fileService.saveOrUpdate(files);
        return Result.success();
    }

    /**
     * 批量删除
     *
     * @param ids 文件ids集合
     * @return Result
     */
    @DeleteMapping("/batch/{ids}")
    public Result deleteBatch(@PathVariable Long[] ids) {

        fileService.list(new LambdaQueryWrapper<Files>().in(Files::getId,ids)).forEach(files -> {
            files.setIsDelete(true);fileService.saveOrUpdate(files);
        });

        return Result.success();
    }

}
