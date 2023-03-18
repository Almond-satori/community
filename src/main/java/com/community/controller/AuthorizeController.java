package com.community.controller;

import com.community.dto.AccessTokenDTO;
import com.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;

    @Value("${github.client.id}")
    private String id;

    @Value("${github.client.secret}")
    private String secret;

    @Value("${github.client.redirectUri}")
    private String redirectUri;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(state);
        accessTokenDTO.setClientId(id);
        accessTokenDTO.setClientSecret(secret);
        accessTokenDTO.setRedirectUri(redirectUri);

        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        githubProvider.getGithubUserInfo(accessToken);

        return "index";
    }
}
