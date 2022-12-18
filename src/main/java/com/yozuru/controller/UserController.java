package com.yozuru.controller;

import com.yozuru.domain.ResponseResult;
import com.yozuru.domain.dto.UserLoginDto;
import com.yozuru.domain.dto.UserRegisterDto;
import com.yozuru.domain.enums.HttpCodeEnum;
import com.yozuru.domain.vo.UserLoginVo;
import com.yozuru.exception.BusinessException;
import com.yozuru.service.UserService;
import com.yozuru.service.impl.LoginServiceImpl;
import com.yozuru.util.SecurityUtils;
import io.jsonwebtoken.lang.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Yozuru
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private LoginServiceImpl loginService;

    @PostMapping("/register")
    public ResponseResult<Object> register(@RequestBody UserRegisterDto userRegisterDto) {
        return userService.register(userRegisterDto);
    }

    @PostMapping("/login")
    public ResponseResult<UserLoginVo> login(@RequestBody UserLoginDto loginDto){
        if (!Strings.hasText(loginDto.getUserName())){
            throw new BusinessException(HttpCodeEnum.REQUIRE_USERNAME);
        }
        return loginService.login(loginDto);
    }

    @PostMapping("/logout")
    public ResponseResult<Object> logout(){
        return loginService.logout();
    }
    @GetMapping("/test")
    public String test(){
        return SecurityUtils.getLoginUser().getUser().getName();
    }
}
