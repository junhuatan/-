package com.liu.org.service;



import com.liu.org.vo.Result;
import com.liu.org.vo.params.PageParams;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FlightServiceTest {

    @Autowired
    private FlightService flightService;

    @Test
    public void test1(){
//        flightService.getFlightByAll("Delhi","Cochin","2019-06-03");
    }

    @Test
    public void test2(){
        PageParams pageParams = new PageParams();
        pageParams.setSource("Delhi");
        pageParams.setDestination("Cochin");
        pageParams.setDateOfJourney("2019-06-03");

        Result result = flightService.listFlight(pageParams);
        System.out.println(result.getData());
    }

    @Test
    public void test3(){
//        flightService.addAid();
        flightService.updateDB();
    }
}
