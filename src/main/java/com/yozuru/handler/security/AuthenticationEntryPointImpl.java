package com.yozuru.handler.security;

import com.alibaba.fastjson.JSON;
import com.yozuru.domain.ResponseResult;
import com.yozuru.domain.enums.HttpCodeEnum;

import com.yozuru.util.WebUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Yozuru
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        authException.printStackTrace();
        //InsufficientAuthenticationException
        //BadCredentialsException
        ResponseResult<Object> result = null;
        if(authException instanceof BadCredentialsException){
            result = ResponseResult.errorResult(HttpCodeEnum.LOGIN_ERROR.getCode(),authException.getMessage());
        }else if(authException instanceof InsufficientAuthenticationException){
            result = ResponseResult.errorResult(HttpCodeEnum.NEED_LOGIN);
        }else{
            result = ResponseResult.errorResult(HttpCodeEnum.SYSTEM_ERROR.getCode(),"认证或授权失败");
        }
        //响应给前端
        WebUtils.renderString(response, JSON.toJSONString(result));
    }
}
