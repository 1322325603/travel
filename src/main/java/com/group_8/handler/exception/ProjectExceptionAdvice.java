package com.group_8.handler.exception;

import com.group_8.domain.ResponseResult;
import com.group_8.domain.enums.HttpCodeEnum;
import com.group_8.exception.BusinessException;
import com.group_8.exception.SystemException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice("com.group_8.controller")
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