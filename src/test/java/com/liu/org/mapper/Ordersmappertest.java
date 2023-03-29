package com.liu.org.mapper;


import com.liu.org.pojo.Flight;
import com.liu.org.pojo.Orders;
import com.liu.org.vo.OrdersVo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class Ordersmappertest {

    @Autowired
    private OrdersMapper ordersMapper;



    @Test
    public void test1(){
        List<OrdersVo> orders = ordersMapper.selectAllOrderAndFlight();

        System.out.println(orders.get(0));
    }
}
