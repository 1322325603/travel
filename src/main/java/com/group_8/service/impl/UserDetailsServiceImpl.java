package com.group_8.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.group_8.domain.entity.LoginUser;
import com.group_8.domain.entity.User;
import com.group_8.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserMapper userMapper;

    /**
     * @param username 请求登录的用户名
     * @return UserDetails
     * &#064;Description  这个方法后续会被AuthenticationManager调用，入参也由AuthenticationManager提供。
     * 作用是根据username来查询出用户详细信息返回。
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //根据用户名查询用户信息
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername,username);
        User user = userMapper.selectOne(wrapper);
        //如果没有查到用户，就抛出异常。
        if (Objects.isNull(user)){
            throw new UsernameNotFoundException("用户名不存在！");
        }

        //如果查到了用户，就封装成UserDetails返回,之后的校验逻辑不需要我们去写。
        return new LoginUser(user, null);
    }
}
