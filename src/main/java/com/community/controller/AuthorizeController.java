package com.community.controller;

import com.community.Bean.User;
import com.community.dto.GithubUserDTO;
import com.community.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Slf4j
@Controller
public class AuthorizeController {

    @Resource
    private UserService userService;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletRequest request){
        GithubUserDTO userDTO = userService.getGithubUserDTO(code,state);

        if(userDTO != null) {
            //在数据库查询或者创建
            User user = userService.getUserByAccessId(userDTO);
            request.getSession().setAttribute("user", user);
            return "redirect:/";
        }
        log.info("未查询到Github的user信息");
        return "redirect:/";
    }
}
