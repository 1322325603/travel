package com.yozuru.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yozuru.domain.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * (User)表数据库访问层
 *
 * @author Yozuru
 * @since 2022-12-17 19:38:28
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}

