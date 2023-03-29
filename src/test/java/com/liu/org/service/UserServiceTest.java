package com.liu.org.service;



import com.liu.org.vo.Result;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void test1(){
        Result register = userService.register("17875803853");
        System.out.println(register);
    }

    @Test
    public void test2(){
        Result register = userService.login("17875803853","17875803853");
        System.out.println(register);
        String data = (String) register.getData();
        userService.logout(data);
    }
}
