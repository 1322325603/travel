package com.yozuru.service.impl;

import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.CreateCache;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yozuru.domain.ResponseResult;
import com.yozuru.domain.constant.RedisConstant;
import com.yozuru.domain.constant.SystemConstant;
import com.yozuru.domain.dto.UserRegisterDto;
import com.yozuru.domain.enums.HttpCodeEnum;
import com.yozuru.mapper.UserMapper;
import com.yozuru.domain.entity.User;
import com.yozuru.service.EmailService;
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
    @Autowired
    private EmailService emailService;

    @CreateCache(area = "emailCode", name = RedisConstant.EMAIL_CODE_KEY_PREFIX,cacheType = CacheType.REMOTE)
    private Cache<Integer, String> emailCodeCache;

    @Override
    public ResponseResult<Object> register(UserRegisterDto userRegisterDto) {
        ResponseResult<Object> checkResult = checkUser(userRegisterDto);
        if (checkResult !=null) {
            return checkResult;
        }
        User user = BeanCopyUtil.copyBean(userRegisterDto, User.class);
        //密码加密
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        //设置用户状态为未激活
        user.setStatus(SystemConstant.USER_INACTIVE);
        save(user);
        //发送激活邮件
        boolean email = emailService.sendActiveEmail(user.getId(), user.getName(), user.getEmail());
        if (email) {
            return ResponseResult.success();
        }
        return ResponseResult.errorResult(HttpCodeEnum.EMAIL_SEND_ERROR);
    }

    @Override
    public boolean activate(Integer uid,String code) {
        String emailCode = emailCodeCache.get(uid);
        //判断验证码是否正确
        if (emailCode == null||!emailCode.equals(code)) {
            return false;
        }
        //如果正确，激活用户
        User user = new User();
        user.setId(uid);
        user.setStatus(SystemConstant.USER_ACTIVE);
        emailCodeCache.remove(uid);
        return updateById(user);
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

