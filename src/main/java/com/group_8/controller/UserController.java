package com.group_8.controller;

import com.group_8.domain.ResponseResult;
import com.group_8.domain.dto.UserEditDto;
import com.group_8.domain.dto.UserLoginDto;
import com.group_8.domain.dto.UserRegisterDto;
import com.group_8.domain.enums.HttpCodeEnum;
import com.group_8.domain.vo.LoginUserInfoVo;
import com.group_8.domain.vo.UserInfoVo;
import com.group_8.domain.vo.UserLoginVo;
import com.group_8.exception.BusinessException;
import com.group_8.service.CheckCodeService;
import com.group_8.service.UserService;
import com.group_8.service.impl.LoginServiceImpl;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


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

    /**
     * 用户注册接口
     * @param userRegisterDto 用户注册信息
     * @return  注册结果
     */
    @PostMapping("/register")
    @ResponseBody
    public ResponseResult<Object> register(@RequestBody UserRegisterDto userRegisterDto) {
        if(checkCodeService.checkCode(request.getRemoteAddr(),userRegisterDto.getCheckCode())) {
            return userService.register(userRegisterDto);
        } else {
            throw new BusinessException(HttpCodeEnum.CHECK_CODE_ERROR);
        }
    }

    /**
     * 用户登录接口
     * @param loginDto 用户登录信息
     * @return  登录结果
     */
    @PostMapping("/login")
    @ResponseBody
    public ResponseResult<UserLoginVo> login(@RequestBody UserLoginDto loginDto){
        if(checkCodeService.checkCode(request.getRemoteAddr(),loginDto.getCheckCode())) {
            return loginService.login(loginDto);
        } else {
            throw new BusinessException(HttpCodeEnum.CHECK_CODE_ERROR);
        }
    }
    /**
     * 用户登出接口
     * 无入参
     * @return  登出结果
     */
    @GetMapping("/logout")
    @ResponseBody
    public ResponseResult<Object> logout(){
        return loginService.logout();
    }
    /**
     * 激活用户
     * @param uid 用户id
     * @param code 激活码
     * @return 激活结果,该结果返回的是一个页面
     */
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
    /**
     * 获取当前登录用户的姓名
     * @return 用户信息
     */
    @GetMapping("/user/loginInfo")
    @ResponseBody
    public ResponseResult<LoginUserInfoVo> getLoginUserInfo(){
        return userService.getLoginUserInfo();
    }
    @GetMapping("/user/info")
    @ResponseBody
    public ResponseResult<UserInfoVo> getUserInfo(){
        return userService.getUserInfo();
    }
    @PutMapping("/user")
    @ResponseBody
    public ResponseResult<Object> updateUserInfo(@RequestBody UserEditDto userEditDto){
        return userService.updateUser(userEditDto);
    }
}
