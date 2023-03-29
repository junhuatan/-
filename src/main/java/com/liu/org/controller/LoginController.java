package com.liu.org.controller;


import com.liu.org.pojo.User;
import com.liu.org.service.AdminService;
import com.liu.org.service.UserService;
import com.liu.org.vo.Result;
import com.liu.org.vo.params.LoginParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("login")
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private AdminService adminService;

    @PostMapping
    public Result login(@RequestBody LoginParam loginParam){
        return userService.login(loginParam.getName(),loginParam.getPassword());
    }

    @GetMapping
    public Result loginAdmin(@RequestHeader("Authorization") String token){
        User user = userService.checkToken(token);
        return adminService.loginAdmin(user);
    }
}
