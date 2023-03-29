package com.liu.org.service;



import com.liu.org.mapper.FlightMapper;
import com.liu.org.pojo.Flight;
import com.liu.org.vo.Result;
import com.liu.org.vo.params.PageParams;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class OrderServiceTest {

    @Autowired
    private OrdersService service;
    @Autowired
    private UserService userService;
    @Autowired
    private FlightMapper flightMapper;
    @Autowired
    private ThreadService threadService;

    @Test
    public void test1(){
//        service.getOrderBy();
        userService.login("17875803853","17875803853");
        service.getRefundOrders(1);
    }

    @Test
    public void test2(){
//        service.getOrderBy();
        Flight flight = flightMapper.selectById(1);
        System.out.println("11111111111111111111111111111111111111111111");
        threadService.updateSeatCount(flightMapper,flight);
    }

    @Test
    public void test3(){
//        service.getOrderBy();
//        System.out.println(service.getSeatNumber(1, "34-D"));
    }
}
