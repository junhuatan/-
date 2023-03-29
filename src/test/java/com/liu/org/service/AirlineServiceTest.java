package com.liu.org.service;



import com.liu.org.vo.Result;
import com.liu.org.vo.params.PageParams;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AirlineServiceTest {

    @Autowired
    private AirlineService airlineService;

    @Test
    public void test3(){
//        flightService.addAid();
        airlineService.updateDB();
    }
}
