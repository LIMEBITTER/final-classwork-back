package com.zxb.common;

import lombok.Getter;

@Getter
public enum ResultCode {
    SUCCESS(200,"操作成功"),
    ERROR(400,"操作失败"),
//    PARAM_ERROR(400,"参数异常"),
    ;

    private Integer code;

    private String msg;

    ResultCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
