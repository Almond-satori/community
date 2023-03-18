package com.community.provider;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONUtil;
import com.community.dto.AccessTokenDTO;
import com.community.dto.GithubUserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class GithubProvider {

    /**
     * 向github发送post请求,返回token
     * @param accessTokenDTO
     * @return token
     */
    public String getAccessToken(AccessTokenDTO accessTokenDTO){
        Map<String, Object> accessTokenMap = BeanUtil.beanToMap(accessTokenDTO, true, true);

        String result = HttpRequest.post("https://github.com/login/oauth/access_token")
                .form(accessTokenMap)//表单内容
                .execute().body();

        //处理result字符串,拿到access_token
        String token = result.split("&")[0].split("=")[1];
        return token;
    }

    /**
     * 向github查询绑定的github用户信息
     * @return GithubUserInfo
     */
    public GithubUserInfo getGithubUserInfo(String accessToken){
        String userInfo = HttpRequest.get("https://api.github.com/user")
                .header("Authorization", "Bearer " + accessToken)
                .execute()
                .body();
        return JSONUtil.toBean(userInfo, GithubUserInfo.class);
    }
}
