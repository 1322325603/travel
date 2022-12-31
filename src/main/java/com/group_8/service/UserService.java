package com.group_8.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.group_8.domain.ResponseResult;
import com.group_8.domain.dto.UserEditDto;
import com.group_8.domain.dto.UserRegisterDto;
import com.group_8.domain.entity.User;
import com.group_8.domain.vo.LoginUserInfoVo;
import com.group_8.domain.vo.UserInfoVo;

import java.util.List;

public interface UserService extends IService<User> {
    /**
     * 用户注册的方法
     * @param userRegisterDto 用户注册的数据传输对象
     * @return 返回注册结果
     */
    ResponseResult<Object> register(UserRegisterDto userRegisterDto);
    /**
     * 用户激活的方法
     * @param uid 用户id
     * @param code 激活码
     * @return 激活结果
     */
    boolean activate(Integer uid,String code);
    /**
     * 查询当前登录用户的信息
     * @return 用户信息、或者未登录。
     */
    ResponseResult<LoginUserInfoVo> getLoginUserInfo();

    ResponseResult<List<UserInfoVo>> getUserList();

    ResponseResult<Object> activationById(Integer id);
    ResponseResult<Object> forbidById(Integer id);

    ResponseResult<UserInfoVo> getUserInfoById(Integer id);
    ResponseResult<UserInfoVo> getUserInfo();

    ResponseResult<Object> updateUser(UserEditDto userEditDto);
}

