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
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Yozuru
 */
@Controller
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
    @ResponseBody
    public ResponseResult<Object> register(@RequestBody UserRegisterDto userRegisterDto) {
        if(checkCodeService.checkCode(request.getRemoteAddr(),userRegisterDto.getCheckCode())) {
            return userService.register(userRegisterDto);
        } else {
            throw new BusinessException(HttpCodeEnum.CHECK_CODE_ERROR);
        }
    }

    @PostMapping("/login")
    @ResponseBody
    public ResponseResult<UserLoginVo> login(@RequestBody UserLoginDto loginDto){
        if(checkCodeService.checkCode(request.getRemoteAddr(),loginDto.getCheckCode())) {
            return loginService.login(loginDto);
        } else {
            throw new BusinessException(HttpCodeEnum.CHECK_CODE_ERROR);
        }
    }

    @PostMapping("/logout")
    @ResponseBody
    public ResponseResult<Object> logout(){
        return loginService.logout();
    }
    @GetMapping("/test")
    public String test(){
        return SecurityUtils.getLoginUser().getUser().getName();
    }
    @GetMapping("/activation")
    public String activation(Integer uid,String code){
        if(Strings.isEmpty(code)||uid==null){
            return null;
        }
        if(userService.activate(uid,code)){
            return "redirect:/activation_ok.html";
        }else {
            return "redirect:/activation_error.html";
        }
    }
}
