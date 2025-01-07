package com.zxb.handler;


import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 属性填充配置类
 * Mybatis Plus 自定义元对象字段填充控制器，实现公共字段自动写入
 */
@Slf4j
@Component
public class GlobalMetaObjectHandler implements MetaObjectHandler {
    /**
     * 插入时候的填充策略
     * @param metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("开始插入填充");
        //添加时候自动填充 setFieldValByName三个参数为：映射类字段，填充值，原对象
        // setFieldValByName(String fieldName, Object fieldVal, MetaObject metaObject)
        //给createTime这个字段和updateTime这俩字段 来一个什么值呢 来一个自动插入时间 传一个数据 这个数据就是mataObject
        this.setFieldValByName("createTime", LocalDateTime.now(), metaObject);

    }

    /**
     * 更新时候的填充策略
     *
     * @param metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("开始更新填充");
        this.setFieldValByName("updateTime", LocalDateTime.now(), metaObject);

    }
}
