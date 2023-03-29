package com.liu.org.service;

import com.liu.org.pojo.Orders;
import com.baomidou.mybatisplus.extension.service.IService;
import com.liu.org.vo.Result;

import java.util.Map;

/**
* @author kiwi
* @description 针对表【orders】的数据库操作Service
* @createDate 2022-11-10 11:17:31
*/
public interface OrdersService extends IService<Orders> {

    Result getOrders();

    Result getOrderByUid(Integer uid);

    Result getRefundOrders(Integer uid);

    Result refund(Integer oid);

    Result upgrade(Map upgradeInfo);

    Result reserve(Orders orders);

//    String getSeatNumber(Integer fid,String rules);

    Result ticketChange(Orders orders);

    Result confirming(Integer[] oids,double[] prices);

    Result getSeatByFid(Integer fid,String seatType);

    Result setSeatNumber(Integer oid, String seatNumber);

    Result getRefundedOrders(Integer uid);

    Result getRefundingOrders(Integer uid);
}

