package com.liu.org.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liu.org.pojo.User;
import com.liu.org.vo.Result;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
* @author kiwi
* @description 针对表【user】的数据库操作Service
* @createDate 2022-10-27 15:11:25
*/
@Transactional
public interface UserService extends IService<User> {

    User findUserByPhone(String phone);

    User findUser(String name,String password);

    Result login(String name, String password);

    User checkToken(String token);

    Result logout(String token);

    Result register(String phone);
}
