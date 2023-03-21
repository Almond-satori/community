package com.community.controller;

import com.community.Bean.User;
import com.community.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@Controller()
public class LoginController {

    @Resource
    UserService userService;

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @PostMapping("/login/verify")
    public String loginVerify(@RequestParam("email") String email,
                              @RequestParam("password") String password){
        User user = userService.loginVerify(email,password);
        if (user == null){
            log.info("用户名或密码错误");
            return "redirect:/";
        }
        return "redirect:/";
    }
}
