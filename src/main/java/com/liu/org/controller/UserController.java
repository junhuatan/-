package com.liu.org.controller;

import com.liu.org.mapper.UserMapper;
import com.liu.org.pojo.User;
import com.liu.org.service.FlightService;
import com.liu.org.service.UserService;
import com.liu.org.vo.ErrorCode;
import com.liu.org.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;


    @GetMapping
    public Result getUser(@RequestHeader("Authorization") String token){
        User user = userService.checkToken(token);
        if(user == null){
            return Result.fail(ErrorCode.ACCOUNT_PWD_NOT_EXIST.getCode(), ErrorCode.ACCOUNT_PWD_NOT_EXIST.getMsg());
        }
        User result = userMapper.selectById(user.getUid());
        return Result.success(result);
    }


}
