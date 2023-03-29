package com.liu.org.mapper;


import com.liu.org.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class Usermappertest {

    @Autowired
    private UserMapper userMapper;



    @Test
    public void test1(){
        User user=new User("17875803853");
        System.out.println(userMapper.insert(user));
    }

    public static void main(String[] args) {
        String str = "34-D";
        String[] split = str.split("-");
        String st1=split[0];
        String st2=split[1];
        System.out.println(st1+st2);

        int i=Integer.parseInt(st1);

        int j=st2.charAt(0);

        System.out.println(i);
        System.out.println(j);
    }
}
