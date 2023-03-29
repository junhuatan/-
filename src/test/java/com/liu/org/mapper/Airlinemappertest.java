package com.liu.org.mapper;


import com.liu.org.pojo.Flight;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class Airlinemappertest {

    @Autowired
    private AirlineMapper airlineMapper;



    @Test
    public void test1(){
        Double indiGo = airlineMapper.selectDiscountByAirlineName("IndiGo");
        Integer i = 1000;
        System.out.println(indiGo*i);
    }


}
