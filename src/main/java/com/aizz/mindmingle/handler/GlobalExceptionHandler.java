package com.aizz.mindmingle.handler;


import com.aizz.mindmingle.common.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public Response<Object> handleException(Exception e) {
        log.error(e.getMessage(), e);
        return Response.error("接口请求失败:" + e.getMessage());
    }

}
