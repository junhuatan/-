package com.liu.org.controller;


import com.liu.org.service.UserService;
import com.liu.org.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("logout")
public class LogoutController {

    @Autowired
    private UserService userService;

    @GetMapping
    public Result logout(@RequestHeader("Authorization") String token){
        return userService.logout(token);
    }
}
