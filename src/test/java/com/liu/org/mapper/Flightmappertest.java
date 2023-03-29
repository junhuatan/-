package com.liu.org.mapper;


import com.liu.org.pojo.Flight;
import com.liu.org.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class Flightmappertest {

    @Autowired
    private FlightMapper flightMapper;



    @Test
    public void test1(){
        Flight flight = flightMapper.selectById(1);
        System.out.println(flight);
    }

    @Test
    public void test2(){
        Flight flight = flightMapper.selectOrdersFlightByFid(1);
        System.out.println(flight);
    }
}
