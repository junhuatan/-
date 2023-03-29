package com.liu.org.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AdminMapperTest {

    @Autowired
    AdminMapper adminMapper;

    @Test
    public void test1(){
//        System.out.println(adminMapper.selectById(1));
        System.out.println(adminMapper.selectOneByUId(1));
    }
}
