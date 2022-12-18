package com.yozuru.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yozuru.domain.ResponseResult;
import com.yozuru.domain.dto.UserRegisterDto;
import com.yozuru.domain.entity.User;

/**
 * (User)表服务接口
 *
 * @author Yozuru
 * @since 2022-12-17 19:38:29
 */

public interface UserService extends IService<User> {

    ResponseResult<Object> register(UserRegisterDto userRegisterDto);
}

