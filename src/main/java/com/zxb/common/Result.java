package com.zxb.common;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class Result implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    //    状态码
    private Integer code;
    //    提示信息message
    private String msg;
    //    返回的属性类型
    private Object data;

    /**
     * 直接返回成功结果
     * @param data
     * @return Result
     */
    public static Result success(Object data) {
        return success(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMsg(), data);
    }

    /**
     * 自定义返回成功结果
     * @param code
     * @param msg
     * @param data
     * @return Result
     */
    public static Result success(Integer code, String msg, Object data) {
        Result r = new Result();
        r.setCode(code);
        r.setMsg(msg);
        r.setData(data);
        return r;
    }

    /**
     * 不带结果直接返回成功
     * @return Result
     */
    public static Result success() {
        Result r = new Result();
        r.setCode(ResultCode.SUCCESS.getCode());
        r.setMsg(ResultCode.SUCCESS.getMsg());
        return r;
    }

    /**
     * 直接返回失败信息
     * @return Result
     */
    public static Result error() {
        return error(ResultCode.ERROR.getCode(), ResultCode.ERROR.getMsg(), null);
    }

    /**
     * 带参数返回失败信息
     * @param msg
     * @return Result
     */
    public static Result error(String msg) {
        return error(ResultCode.ERROR.getCode(), msg, null);
    }

    /**
     * 自定义返回失败信息
     * @param code
     * @param msg
     * @param data
     * @return Result
     */
    public static Result error(Integer code, String msg, Object data) {
        Result r = new Result();
        r.setCode(code);
        r.setMsg(msg);
        r.setData(data);
        return r;
    }
}
