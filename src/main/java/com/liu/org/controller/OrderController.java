package com.liu.org.controller;

import com.liu.org.pojo.Orders;
import com.liu.org.service.OrdersService;
import com.liu.org.vo.ErrorCode;
import com.liu.org.vo.Result;
import com.liu.org.vo.params.OidListParam;
import com.liu.org.vo.params.SeatParam;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrdersService ordersService;

    // 预订机票
    @PostMapping("/reserve")
    public Result reserve(@RequestBody Orders orders){
        System.out.println(orders);
        return ordersService.reserve(orders);
    }

    //付款成功
    @PostMapping("/confirming" )
    public Result confirming(@RequestBody OidListParam oidListParam){
        Integer[] oids = oidListParam.getOids();
        if(oids == null){
            return Result.fail(ErrorCode.NO_HAVE_ORDERS.getCode(), ErrorCode.NO_HAVE_ORDERS.getMsg());
        }
        double[] prices = oidListParam.getPrices();
        if(prices == null){
            return Result.fail(ErrorCode.NO_HAVE_ORDERS.getCode(), ErrorCode.NO_HAVE_ORDERS.getMsg());
        }
        return ordersService.confirming(oids,prices);
    }

    // 管理系统查询所有订单
    @GetMapping("/order")
    public Result getOrders(){
        return ordersService.getOrders();
    }

    // 返回fid的座位信息
    @PostMapping("/getSeat")
    public Result getSeatByFid(@RequestBody SeatParam seatParam){
        return ordersService.getSeatByFid(seatParam.getFid(),seatParam.getSeatType());
    }

    //换座
    @PostMapping("/setSeat")
    public Result setSeatNumber(@RequestBody SeatParam seatParam){
        String[] split = seatParam.getSeatNumber().split("-");

        String seatNumber = Integer.parseInt(split[0]) + "-" + (char)( Integer.parseInt(split[1])+64);

        return ordersService.setSeatNumber(seatParam.getOid(),seatNumber);
    }

    // 查询某订票人所有订单
    @GetMapping("/order/{uid}")
    public Result getOrderByUid(@PathVariable Integer uid){
        return ordersService.getOrderByUid(uid);
    }

    // 查询某订票人已退订的所有订单
    @GetMapping("/refundOrders/{uid}")
    public Result getRefundOrders(@PathVariable Integer uid){
        return ordersService.getRefundOrders(uid);
    }

    // 查询某订票人已完成的所有订单
    @GetMapping("/refundedOrders/{uid}")
    public Result getRefundedOrders(@PathVariable Integer uid){
        return ordersService.getRefundedOrders(uid);
    }

    // 查询某订票人未支付的所有订单
    @GetMapping("/refundingOrders/{uid}")
    public Result getRefundingOrders(@PathVariable Integer uid){
        return ordersService.getRefundingOrders(uid);
    }

    // 取消订单
    @GetMapping("/refund")
    public Result refund(@RequestParam("oid") Integer oid){
        System.out.println("666666666:"+oid);
        return ordersService.refund(oid);
    }

    // 改签
    /**
     * json参数传递：
      {
          "oid" : 1,
          "fid" : 2,
      }
     * @param
     * @return
     */
    @PostMapping("/ticketChange")
    public Result ticketChange(@RequestBody Orders orders){

        return ordersService.ticketChange(orders);
    }


    // 升舱
    /**
     *  业务步骤：
     * （1）、订单id传过来，修改航班的seat_type为F（头等舱）
     * （2）、根据订单id查询航班，获取航班绑定的飞机信息
     * （3）、将该飞机的经济舱座位退回（+1），头等舱（-1）——————（是否存在座位可查询飞机信息在前台渲染可直观查看）
     *  json参数传递：
          {
            "oid" : 1,
            "seatNumber" : "3A"
          }
     * @param upgradeInfo
     * @return
     */
    @PostMapping("/upgrade")
    public Result upgrade(@RequestBody Map upgradeInfo){
        return ordersService.upgrade(upgradeInfo);
    }

}
