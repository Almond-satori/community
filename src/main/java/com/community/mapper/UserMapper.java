package com.community.mapper;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.community.Bean.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
@TableName("t_user")
public interface UserMapper extends BaseMapper<User> {
}
