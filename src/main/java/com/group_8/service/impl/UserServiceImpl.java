package com.group_8.service.impl;

import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.CreateCache;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.group_8.domain.ResponseResult;
import com.group_8.domain.constant.RedisConstant;
import com.group_8.domain.constant.SystemConstant;
import com.group_8.domain.dto.UserEditDto;
import com.group_8.domain.dto.UserRegisterDto;
import com.group_8.domain.entity.LoginUser;
import com.group_8.domain.enums.HttpCodeEnum;
import com.group_8.domain.vo.LoginUserInfoVo;
import com.group_8.domain.vo.UserInfoVo;
import com.group_8.mapper.UserMapper;
import com.group_8.domain.entity.User;
import com.group_8.service.EmailService;
import com.group_8.service.UserService;
import com.group_8.util.BeanCopyUtil;
import com.group_8.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;



@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private EmailService emailService;

    @CreateCache(area = "userLogin", name = RedisConstant.USER_KEY_PREFIX, cacheType = CacheType.REMOTE)
    private Cache<Integer, LoginUser> userDetailCache;

    @CreateCache(area = "emailCode", name = RedisConstant.EMAIL_CODE_KEY_PREFIX,cacheType = CacheType.REMOTE)
    private Cache<Integer, String> emailCodeCache;

    @Override
    @Transactional
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

    @Override
    public ResponseResult<LoginUserInfoVo> getLoginUserInfo() {
        User loginUser = SecurityUtils.getLoginUser().getUser();
        if (loginUser == null) {
            return ResponseResult.errorResult(HttpCodeEnum.NEED_LOGIN);
        }
        LoginUserInfoVo loginUserInfoVo = BeanCopyUtil.copyBean(loginUser, LoginUserInfoVo.class);
        return ResponseResult.success(loginUserInfoVo);
    }

    @Override
    public ResponseResult<UserInfoVo> getUserInfo() {
        User loginUser = SecurityUtils.getLoginUser().getUser();
        if (loginUser == null) {
            return ResponseResult.errorResult(HttpCodeEnum.NEED_LOGIN);
        }
        UserInfoVo userInfoVo = BeanCopyUtil.copyBean(loginUser, UserInfoVo.class);
        return ResponseResult.success(userInfoVo);
    }

    @Override
    public ResponseResult<List<UserInfoVo>> getUserList() {
        if (!SecurityUtils.isAdmin()) {
            return ResponseResult.errorResult(HttpCodeEnum.NO_OPERATOR_AUTH);
        }
        List<User> userList = list();
        List<UserInfoVo> userInfoVoList = BeanCopyUtil.copyBeanList(userList, UserInfoVo.class);
        return ResponseResult.success(userInfoVoList);
    }

    @Override
    public ResponseResult<Object> activationById(Integer id) {
        if (!SecurityUtils.isAdmin()) {
            return ResponseResult.errorResult(HttpCodeEnum.NO_OPERATOR_AUTH);
        }
        User user = new User();
        user.setId(id);
        user.setStatus(SystemConstant.USER_ACTIVE);
        updateById(user);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult<Object> forbidById(Integer id) {
        if (!SecurityUtils.isAdmin()) {
            return ResponseResult.errorResult(HttpCodeEnum.NO_OPERATOR_AUTH);
        }
        User user = new User();
        user.setId(id);
        user.setStatus(SystemConstant.USER_FORBIDDEN);
        updateById(user);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult<UserInfoVo> getUserInfoById(Integer id) {
        if (!SecurityUtils.isAdmin()) {
            return ResponseResult.errorResult(HttpCodeEnum.NO_OPERATOR_AUTH);
        }
        User user = getById(id);
        UserInfoVo userInfoVo = BeanCopyUtil.copyBean(user, UserInfoVo.class);
        return ResponseResult.success(userInfoVo);
    }

    @Override
    public ResponseResult<Object> updateUser(UserEditDto userEditDto) {
        if (!SecurityUtils.isAdmin()&&!userEditDto.getId().equals(SecurityUtils.getUserId())) {
            return ResponseResult.errorResult(HttpCodeEnum.NO_OPERATOR_AUTH);
        }
        User user = BeanCopyUtil.copyBean(userEditDto, User.class);
        if (null!=userEditDto.getPassword()) {
            user.setPassword(passwordEncoder.encode(userEditDto.getPassword()));
        }
        if(updateById(user)){
            LoginUser loginUser=new LoginUser(getById(user.getId()),null);
            userDetailCache.put(user.getId(),loginUser);
        }
        System.out.println("真的不想写了！！！！！！！！！！！！！");
        return ResponseResult.success();
    }
}

