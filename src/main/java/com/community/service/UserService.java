package com.community.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.community.Bean.User;


public interface UserService extends IService<User> {

    User loginVerify(String email, String password);

    boolean register(String email, String password);
}
