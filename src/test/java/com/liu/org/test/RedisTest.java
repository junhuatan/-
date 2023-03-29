package com.liu.org.test;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.BoundZSetOperations;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
public class RedisTest {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void testKeyBoundOperation(){
        redisTemplate.boundValueOps("names").set("谭骏桦");
        Object name = redisTemplate.boundValueOps("names").get();
        System.out.println(name);
    }

    @Test
    public void testZSetBoundOperation(){
        BoundZSetOperations ttttttt = redisTemplate.boundZSetOps("ttttttt");
        System.out.println("--------------------------------------------");
        System.out.println(ttttttt.size());
        System.out.println("--------------------------------------------");

    }
}
