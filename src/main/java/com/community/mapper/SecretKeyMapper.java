package com.community.mapper;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.community.Bean.UserKey;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SecretKeyMapper extends BaseMapper<UserKey> {
}
