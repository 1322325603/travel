package com.group_8.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.group_8.domain.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {

}

