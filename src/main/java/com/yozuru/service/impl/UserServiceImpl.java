package com.yozuru.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yozuru.domain.ResponseResult;
import com.yozuru.domain.dto.UserRegisterDto;
import com.yozuru.domain.enums.HttpCodeEnum;
import com.yozuru.mapper.UserMapper;
import com.yozuru.domain.entity.User;
import com.yozuru.service.UserService;
import com.yozuru.util.BeanCopyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * (User)表服务实现类
 *
 * @author Yozuru
 * @since 2022-12-17 19:38:29
 */

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public ResponseResult<Object> register(UserRegisterDto userRegisterDto) {
        ResponseResult<Object> checkResult = checkUser(userRegisterDto);
        if (checkResult !=null) {
            return checkResult;
        }
        User user = BeanCopyUtil.copyBean(userRegisterDto, User.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        save(user);
        return ResponseResult.success();
    }

    private ResponseResult<Object> checkUser(UserRegisterDto userRegisterDto) {
        // 1. 判断用户名是否存在
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, userRegisterDto.getUsername());
        if (count(queryWrapper) > 0) {
            return ResponseResult.errorResult(HttpCodeEnum.USERNAME_EXIST);
        }
        // 2. 判断邮箱是否存在
        queryWrapper.clear();
        queryWrapper.eq(User::getEmail, userRegisterDto.getEmail());
        if (count(queryWrapper) > 0) {
            return ResponseResult.errorResult(HttpCodeEnum.EMAIL_EXIST);
        }
        // 3. 判断手机号是否存在
        queryWrapper.clear();
        queryWrapper.eq(User::getTelephone, userRegisterDto.getTelephone());
        if (count(queryWrapper) > 0) {
            return ResponseResult.errorResult(HttpCodeEnum.PHONENUMBER_EXIST);
        }
        return null;
    }
}
