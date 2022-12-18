package com.yozuru.controller;

import com.yozuru.domain.ResponseResult;
import com.yozuru.domain.vo.CheckCodeVo;
import com.yozuru.service.CheckCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Yozuru
 */

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
