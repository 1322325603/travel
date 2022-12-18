package com.yozuru.exception;

import com.yozuru.domain.enums.HttpCodeEnum;
import lombok.Getter;
import lombok.ToString;

/**
 * @author Yozuru
 */
@Getter
@ToString
public class SystemException extends RuntimeException{
    private final Integer code;
    private final String message;

    public SystemException(HttpCodeEnum httpCodeEnum){
        super(httpCodeEnum.getMessage());
        this.code=httpCodeEnum.getCode();
        this.message= httpCodeEnum.getMessage();
    }
}
