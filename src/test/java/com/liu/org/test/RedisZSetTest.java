package com.liu.org.test;


import com.liu.org.pojo.Flight;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.*;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.HashSet;
import java.util.Set;

/***
 * @Author 徐庶   QQ:1092002729
 * @Slogan 致敬大师，致敬未来的你
 *
 * zset
 */
@SpringBootTest
public class RedisZSetTest {

    // 它是线程安全的
    @Autowired
    RedisTemplate redisTemplate;


    // 添加
    @Test
    public void testAdd() {
        BoundZSetOperations zset = redisTemplate.boundZSetOps("zset");
        Flight flight = new Flight();
        zset.add(flight,100);
        zset.add("张三",100);
        zset.add("李四",60);
        zset.add("王五",80);
        zset.add("赵六",70);
        System.out.println(zset.range(0, 2));
    }

    @Test
    public void testGet() {
        BoundZSetOperations zset = redisTemplate.boundZSetOps("zset");
        // 根据元素获取分数
        System.out.println(zset.score("张三"));
        // 个数
        System.out.println(zset.size());
        // 原子添加并且返回
        System.out.println(zset.incrementScore("李四", 1));

    }

    @Test
    public void testGetRange() {
        BoundZSetOperations zset = redisTemplate.boundZSetOps("zset");
        // 范围升序查询

        // 升序顺序范围
        System.out.println(zset.range(0, 2));
        // 分数范围
        System.out.println(zset.rangeByScore(60,90));
        // 带分数
        System.out.println(zset.rangeWithScores(0, 2));
        System.out.println(zset.rangeByScoreWithScores(60,90));

        System.out.println("--------------------");

        // 范围降序查询
        // 降序顺序范围
        Set set = zset.reverseRange(0, 2);
        System.out.println(set);
        System.out.println("--------------------");
        System.out.println(zset.reverseRange(0, 2));
        // 降序分数范围
        System.out.println(zset.reverseRangeByScore(60,90));
        // 降序带分数
        System.out.println(zset.reverseRangeWithScores(0, 2));
        System.out.println(zset.reverseRangeByScoreWithScores(60,90));

    }

    @Test
    public void testDel() {
        BoundZSetOperations zset = redisTemplate.boundZSetOps("zset");

        System.out.println(zset.remove("张三"));

        // 按照升序范围移除
        zset.removeRange(0,1);
        zset.removeRangeByScore(100,90);
    }



    // 统计每日新闻top10
    @Test
    public void test() {

        BoundZSetOperations zset = redisTemplate.boundZSetOps("news20000101");

        //
        zset.incrementScore("守护香港",1);

        System.out.println(zset.reverseRangeWithScores(0, 9));

        redisTemplate.convertAndSend("hello!", "world");
    }
}
