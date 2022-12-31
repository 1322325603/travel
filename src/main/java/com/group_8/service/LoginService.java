package com.group_8.service;


import com.group_8.domain.ResponseResult;
import com.group_8.domain.dto.UserLoginDto;
import com.group_8.domain.vo.UserLoginVo;


public interface LoginService {
    ResponseResult<UserLoginVo> login(UserLoginDto loginDto);

    ResponseResult<Object> logout();
}