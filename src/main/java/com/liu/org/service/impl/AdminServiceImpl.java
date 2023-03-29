package com.liu.org.service.impl;

import com.liu.org.mapper.AdminMapper;
import com.liu.org.mapper.UserMapper;
import com.liu.org.pojo.User;
import com.liu.org.service.AdminService;
import com.liu.org.vo.ErrorCode;
import com.liu.org.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public Result loginAdmin(User user) {

        if(adminMapper.selectOneByUId(user.getUid()) == 0){
            return Result.fail(ErrorCode.ADMIN_NOT_EXIST.getCode(), ErrorCode.ADMIN_NOT_EXIST.getMsg());
        }
        return Result.success(user.getUsername());
    }
}
