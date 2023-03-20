package com.community.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.community.Bean.User;
import com.community.dto.GithubUserDTO;

public interface UserService extends IService<User> {

    GithubUserDTO getGithubUserDTO(String code, String state);

    User getUserByAccessId(GithubUserDTO userDTO);
}
