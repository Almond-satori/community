package com.community.service.impl;


import cn.hutool.crypto.symmetric.AES;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.community.Bean.UserKey;
import com.community.Bean.User;
import com.community.mapper.UserMapper;
import com.community.service.SecretKeyService;
import com.community.service.UserService;
import com.community.utils.AESGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    SecretKeyService secretKeyService;

    @Autowired
    UserMapper userMapper;

    public static final String EMAIL_REGEX_DEFAULT = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

    /**
     * 验证邮箱和密码是否匹配
     * @param email
     * @param password
     * @return
     */
    @Override
    public User loginVerify(String email, String password) {
        if(email == null || password == null) return null;
        //查找用户
        User user = query().eq("email", email).one();
        if(user == null) return null;
        //查找秘钥
        UserKey userKey = secretKeyService.getById(user.getId());
        //解密出密码与登录数据比较
        String DBpsw = AESGenerator.decode(user.getPassword(), userKey.getSecretKey());
        if (!password.equals(DBpsw)) {
            return null;
        }
        return user;
    }

    @Override
    public boolean register(String email, String password) {
        if(!isEmail(email)) return false;
        if(password.isEmpty()) return false;
        int len = password.length();
        if (len < 6 || len > 16) return false;

        User user = new User();
        user.setEmail(email);
        //先存入邮箱,mybatisPlus会自动对id使用雪花算法,生成id
        save(user);
        //根据邮箱查询这个id
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("email", email);
        int id = getOne(queryWrapper).getId();
        //根据id设置密文密码
        String key = AESGenerator.generateKey(id);
        String cypherText = AESGenerator.generateCypherText(password, key);
        //更新密文密码到数据库
        LambdaUpdateWrapper<User> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(User::getId, id)
                .set(User::getPassword, cypherText);
        update(wrapper);
        //设置秘钥
        secretKeyService.save(new UserKey(id,key));
        return true;
    }


    public static boolean isEmail(String email) {
        return email.matches(EMAIL_REGEX_DEFAULT);
    }
}
