package com.community.controller;

import com.community.dto.AccessTokenDTO;
import com.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(state);
        accessTokenDTO.setRedirectUri("https://localhost:8887/callback");
        accessTokenDTO.setClientSecret("3ca92b8f1cf860a15bb20ebc5e05ac14940df679");
        accessTokenDTO.setClientId("acc36aa2d2ade5768267");

        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        githubProvider.getGithubUserInfo(accessToken);

        return "index";
    }
}
