package cn.escort.frameworkConfig.web.exceptionHandler.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@Slf4j
@RestControllerAdvice
@Order(1)
public class GlobalExceptionHandler {

    /**
     * 默认全局异常处理。
     *
     * @param e e
     * @return ResponseData
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseData<String> exception(Exception e) {
        log.error("兜底异常信息 ex={}", e.getMessage());
        return ResponseData.fail(ResponseCode.RC500.getCode(), e.getMessage());
    }

    *//**
     * Assert异常
     *//*
    @ExceptionHandler({IllegalArgumentException.class, IllegalStateException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseData<String> exception(IllegalArgumentException e) {
        return ResponseData.fail(ResponseCode.ILLEGAL_ARGUMENT.getCode(), e.getMessage());
    }


    *//**
     * 抓取自定义异常  BaseException
     *//*
    @ExceptionHandler(BaseException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseData<String> exception(BaseException e) {
        return ResponseData.fail(e.getErrorCode(), e.getMessage());
    }*/


}
