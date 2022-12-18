package com.yozuru.handler.exception;

import com.yozuru.domain.ResponseResult;
import com.yozuru.domain.enums.HttpCodeEnum;
import com.yozuru.exception.BusinessException;
import com.yozuru.exception.SystemException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice("com.yozuru.controller")
public class ProjectExceptionAdvice {
//    优化方案：自定义多种异常，然后拦截异常抛出我们的自定异常。最终在这里分类处理
    @ExceptionHandler({BusinessException.class})
    public ResponseResult<Object> toBusinessException(BusinessException e){
        return ResponseResult.errorResult(e.getCode(), e.getMessage());
    }
    @ExceptionHandler({SystemException.class})
    public ResponseResult<Object> toSystemException(SystemException e){
        return ResponseResult.errorResult(e.getCode(), e.getMessage());
    }
    //参数校验异常
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseResult<Object> illegalParameterException(MethodArgumentNotValidException e){
        String message = e.getMessage();
        message = message.substring(message.lastIndexOf("[")+1, message.lastIndexOf("]")-1);
        return ResponseResult.errorResult(HttpCodeEnum.ILLEGAL_PARAMETER.getCode(),message);
    }
    @ExceptionHandler
    public ResponseResult<Object> toUnknownException(Exception e){
        //记日志，找运维，找开发
        e.printStackTrace();
        return ResponseResult.errorResult(HttpCodeEnum.SYSTEM_ERROR);
    }

}