package com.community.controller;

import com.community.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RequestMapping("/register")
@Controller()
public class RegisterController {

    @Resource
    private UserService userService;

    @GetMapping()
    public String register(){
        return "register";
    }

    @PostMapping("/verify")
    public String registerVerify(@RequestParam("email") String email,
                                 @RequestParam("password") String password){
        userService.register(email,password);
        return "login";
    }

}

