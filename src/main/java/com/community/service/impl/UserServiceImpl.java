package com.community.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.community.Bean.User;
import com.community.dto.AccessTokenDTO;
import com.community.dto.GithubUserDTO;
import com.community.mapper.UserMapper;
import com.community.provider.GithubProvider;
import com.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Value("${github.client.id}")
    private String id;

    @Value("${github.client.secret}")
    private String secret;

    @Value("${github.client.redirectUri}")
    private String redirectUri;

    @Autowired
    private GithubProvider githubProvider;

    @Autowired
    private UserMapper userMapper;

    /**
     * 获取Github用户信息
     * @param code
     * @param state
     * @return
     */
    @Override
    public GithubUserDTO getGithubUserDTO(String code, String state) {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(state);
        accessTokenDTO.setClientId(id);
        accessTokenDTO.setClientSecret(secret);
        accessTokenDTO.setRedirectUri(redirectUri);

        String accessToken = githubProvider.getAccessToken(accessTokenDTO);

        return githubProvider.getGithubUserInfo(accessToken);
    }

    /**
     * 通过github用户信息中的id(即数据库中的access_id),获取对应用户信息
     * 如果没有查到任何用户,则在数据库中创建该用户
     * @param userDTO
     * @return User
     */
    @Override
    public User getUserByAccessId(GithubUserDTO userDTO) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getAccountId,userDTO.getId());
        User user = userMapper.selectOne(wrapper);
        if (user == null) {
            user = new User();
            user.setToken(UUID.randomUUID().toString());
            user.setAccountId(String.valueOf(userDTO.getId()));
            user.setName(userDTO.getLogin());
            long time = System.currentTimeMillis();
            user.setCreateTime(time);
            user.setModifiedTime(time);
        }
        return user;
    }
}
