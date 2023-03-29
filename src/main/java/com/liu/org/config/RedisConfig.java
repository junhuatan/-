package com.liu.org.config;

import com.liu.org.pojo.Flight;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig{
    // 2. redis模板类（工具类）
    @Bean
    public RedisTemplate redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        // StringRedisSerializer 只能传入String类型的值（存入redis之前就要转换成String)
        //redisTemplate.setDefaultSerializer(new StringRedisSerializer());

        // 改成Jackson2JsonRedisSerializer 推荐
        redisTemplate.setDefaultSerializer(new Jackson2JsonRedisSerializer(Object.class));

        return redisTemplate;
    }
}