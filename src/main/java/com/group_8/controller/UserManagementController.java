package com.group_8.controller;

import com.group_8.domain.ResponseResult;
import com.group_8.domain.dto.UserEditDto;
import com.group_8.domain.vo.UserInfoVo;
import com.group_8.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController
@RequestMapping("/user_management")
public class UserManagementController {
    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public ResponseResult<List<UserInfoVo>> list() {
        return userService.getUserList();
    }
    @GetMapping("/activation/{id}")
    public ResponseResult<Object> activation(@PathVariable Integer id) {
        return userService.activationById(id);
    }
    @GetMapping("/forbid/{id}")
    public ResponseResult<Object> forbid(@PathVariable Integer id) {
        return userService.forbidById(id);
    }
    @GetMapping("/info/{id}")
    public ResponseResult<UserInfoVo> getInfo(@PathVariable Integer id) {
        return userService.getUserInfoById(id);
    }
    @PutMapping
    public ResponseResult<Object> updateUser(@RequestBody UserEditDto userEditDto) {
        return userService.updateUser(userEditDto);
    }

}
