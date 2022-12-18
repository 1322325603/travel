package com.yozuru.controller;

import com.yozuru.domain.ResponseResult;
import com.yozuru.domain.vo.CheckCodeVo;
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

    @GetMapping
    public ResponseResult<CheckCodeVo> getCheckCode(HttpServletRequest request){
        return null;
    }

}
