package com.zxb.handler;


import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.exception.SaTokenException;
import cn.dev33.satoken.util.SaResult;
import cn.hutool.core.io.IORuntimeException;
import com.zxb.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常拦截（拦截项目中所有的异常）
 */

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {



    @ExceptionHandler(SaTokenException.class)
    public SaResult handlerSaTokenException(SaTokenException e) {

        /**
         * Sa-Token异常细分状态码
         * 11041：缺少指定角色; 11051：缺少指定权限
         * 抛出403
         */
        if (e.getCode()>=11041  && e.getCode() <= 11051) {
            SaHolder.getResponse().setStatus(HttpStatus.FORBIDDEN.value());
            return SaResult.error("暂无权限！");
        }

        // 更多 code 码判断 ... 这边直接返回code
        SaHolder.getResponse().setStatus(e.getCode());
        // 默认的提示
        return SaResult.error(e.getMessage());
    }

    @ExceptionHandler(IORuntimeException.class)
    public void handlerIORuntimeException(IORuntimeException e){
        log.error("文件找不到");
    }

    @ExceptionHandler(RuntimeException.class)
    public Result handlerRuntimeException(RuntimeException e){
        log.error(e.getMessage());
        e.printStackTrace();
        SaHolder.getResponse().setStatus(HttpStatus.BAD_REQUEST.value());
        return Result.error(e.getMessage());
    }

}
