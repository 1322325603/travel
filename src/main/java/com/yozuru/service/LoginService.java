package com.yozuru.service;


import com.yozuru.domain.ResponseResult;
import com.yozuru.domain.dto.UserLoginDto;
import com.yozuru.domain.vo.UserLoginVo;

/**
 * 前台登录服务接口
 *
 * @author Yozuru
 */

public interface LoginService {
    ResponseResult<UserLoginVo> login(UserLoginDto loginDto);

    ResponseResult<Object> logout();
}