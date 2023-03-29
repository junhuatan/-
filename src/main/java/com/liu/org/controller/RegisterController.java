package com.liu.org.controller;

import com.liu.org.service.UserService;
import com.liu.org.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("register")
public class RegisterController {

    @Autowired
    private UserService userService;

    @PostMapping
    public Result register(String phone) {
        return userService.register(phone);
    }
}
