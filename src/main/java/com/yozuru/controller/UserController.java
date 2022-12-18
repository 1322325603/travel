package com.yozuru.controller;

import com.yozuru.domain.ResponseResult;
import com.yozuru.domain.dto.UserLoginDto;
import com.yozuru.domain.dto.UserRegisterDto;
import com.yozuru.domain.enums.HttpCodeEnum;
import com.yozuru.domain.vo.UserLoginVo;
import com.yozuru.exception.BusinessException;
import com.yozuru.service.CheckCodeService;
import com.yozuru.service.UserService;
import com.yozuru.service.impl.LoginServiceImpl;
import com.yozuru.util.SecurityUtils;
import io.jsonwebtoken.lang.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Yozuru
 */
@RestController
public class UserController {

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private UserService userService;
    @Autowired
    private LoginServiceImpl loginService;
    @Autowired
    private CheckCodeService checkCodeService;

    @PostMapping("/register")
    public ResponseResult<Object> register(@RequestBody UserRegisterDto userRegisterDto) {
        if(checkCodeService.checkCode(request.getRemoteAddr(),userRegisterDto.getCheckCode())) {
            return userService.register(userRegisterDto);
        } else {
            throw new BusinessException(HttpCodeEnum.CHECK_CODE_ERROR);
        }
    }

    @PostMapping("/login")
    public ResponseResult<UserLoginVo> login(@RequestBody UserLoginDto loginDto){
        if(checkCodeService.checkCode(request.getRemoteAddr(),loginDto.getCheckCode())) {
            return loginService.login(loginDto);
        } else {
            throw new BusinessException(HttpCodeEnum.CHECK_CODE_ERROR);
        }
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
