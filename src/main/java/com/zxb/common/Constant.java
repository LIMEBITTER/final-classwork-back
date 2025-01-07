package com.zxb.common;

import org.springframework.beans.factory.annotation.Value;

import java.time.format.DateTimeFormatter;

public class Constant {

    //    rolePermsList缓存前缀
    public static String ROLE_PREFIX="rolePerms";
    //    permissionPermList缓存前缀
    public static String PERMISSION_PREFIX="permissionPerms";
    //    router缓存前缀
    public static String ROUTERS_PREFIX="routers";
    //    captcha缓存前缀(验证码)
    public static String CAPTCHA_PREFIX="captcha";
    //    onlineUser缓存前缀
    public static String ONLINE_PREFIX="online";
    //    设置默认密码
    public static String DEFAULT_PASSWORD="12345";
    //    设置过滤数据库表的前缀
    public static String TABLE_PREFIX="tb_";
    //    设置包名
    public static String PACKAGE_NAME="com.zxb";

    //    设置默认角色
    public static Long DEFAULT_ROLE=6L;
    //    邮箱前缀
    public static String EMAIL_PREFIX="email";
    // 后端上传文件地址
    public static String FILE_UPLOAD_ADDRESS = "http://localhost:90/api/file/";


    //工单前缀
    public static final String PREFIX = "WF";
    //工单固定日期格式
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    public static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";




}
