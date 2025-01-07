package com.zxb.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 封装的路由数据
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Meta implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 图标
     */
    private String icon;
    /**
     * 标题
     */
    private String title;
    /**
     * 是否隐藏
     */
    private boolean hidden;

}
