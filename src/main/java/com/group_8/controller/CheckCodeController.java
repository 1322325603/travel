package com.group_8.controller;

import com.group_8.domain.ResponseResult;
import com.group_8.domain.vo.CheckCodeVo;
import com.group_8.service.CheckCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/checkCode")
public class CheckCodeController {
    @Autowired
    private CheckCodeService checkCodeService;
    @Autowired
    private HttpServletRequest request;

    @GetMapping
    public ResponseResult<CheckCodeVo> getCheckCode(){
        //获取请求用户的ip地址，作为验证码的key
        String addr = request.getRemoteAddr();
        return checkCodeService.getCheckCode(addr);
    }
}
