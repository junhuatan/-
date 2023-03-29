package com.liu.org.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liu.org.Utils.UserThreadLocal;
import com.liu.org.mapper.AirplaneMapper;
import com.liu.org.mapper.FlightMapper;
import com.liu.org.pojo.Airplane;
import com.liu.org.pojo.Flight;
import com.liu.org.pojo.Orders;
import com.liu.org.service.OrdersService;
import com.liu.org.mapper.OrdersMapper;
import com.liu.org.service.ThreadService;
import com.liu.org.vo.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
* @author kiwi
* @description 针对表【orders】的数据库操作Service实现
* @createDate 2022-11-10 11:17:31
*/
@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders>
    implements OrdersService{

    @Autowired
    private OrdersMapper ordersMapper;

    @Autowired
    private FlightMapper flightMapper;

    @Autowired
    private ThreadService threadService;

    // 管理系统查询所有订单
    @Override
    public Result getOrders() {
        List<OrdersVo> orderList = ordersMapper.selectAllOrderAndFlight();
        if (orderList != null){

            return Result.success(orderList);
        }else{
            return Result.fail(ErrorCode.NO_HAVE_ORDERS.getCode(), ErrorCode.NO_HAVE_ORDERS.getMsg());
        }
    }

    // 查询某订票人所有订单
    @Override
    public Result getOrderByUid(Integer uid) {
//        if (!UserThreadLocal.get().equals(uid)) {
//            return Result.fail(ErrorCode.ACCOUNT_NOCHECK.getCode(), ErrorCode.ACCOUNT_NOCHECK.getMsg());
//        }
        List<OrdersVo> ordersVoList = ordersMapper.selectAllOrderAndFlightByUid(1,0);
        return Result.success(ordersVoList);
    }

    // 查询某订票人已退订的所有订单
    @Override
    public Result getRefundOrders(Integer uid) {
//        if (!UserThreadLocal.get().equals(uid)) {
//            return Result.fail(ErrorCode.ACCOUNT_NOCHECK.getCode(), ErrorCode.ACCOUNT_NOCHECK.getMsg());
//        }
        List<OrdersVo> ordersVoList = ordersMapper.selectAllOrderAndFlightByUid(1,1);
        return Result.success(ordersVoList);
    }

    // 取消订单
    @Override
    public Result refund(Integer oid) {
        System.out.println("订单编号"+oid);
        //UpdateWrapper更新操作
        UpdateWrapper<Orders> warp = new UpdateWrapper<>();
        if (oid != null){
            // 将refund状态改为1（表示已退订）
            warp.set("refund",1).eq("oid",oid);
            int result = ordersMapper.update(null,warp);
            if (result > 0){
                return Result.success(null);
            }else {
                return Result.fail(ErrorCode.ERROR_ORDERS_UPDATED.getCode(), ErrorCode.ERROR_ORDERS_UPDATED.getMsg());
            }
        }else{
            return Result.fail(ErrorCode.NO_HAVE_ORDERS.getCode(), ErrorCode.NO_HAVE_ORDERS.getMsg());
        }
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
     "seatNumber" : "A3"
     }
     * @param upgradeInfo
     * @return
     */
    @Override
    public Result upgrade(Map upgradeInfo) {
        Integer oid = (Integer) upgradeInfo.get("oid");
        String seatNumber = (String) upgradeInfo.get("seatNumber");
        //取得信息
        Orders orders = ordersMapper.selectById(oid);
        Flight flight = flightMapper.selectById(orders.getFid());
        //改订单
        orders.setSeatType("F");
        orders.setSeatNumber(seatNumber);
        orders.setPrice(flight.getClassPrice());
        //判断数据是否被抢位
        LambdaQueryWrapper<Orders> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Orders::getFid,orders.getFid());
        queryWrapper.eq(Orders::getSeatType, "Y");
        queryWrapper.eq(Orders::getSeatNumber,seatNumber);
        int result = ordersMapper.selectCount(queryWrapper);
        if(result != 0){
            return Result.fail(ErrorCode.ERROR_ORDERS_UPGRADE.getCode(), ErrorCode.ERROR_ORDERS_UPGRADE.getMsg());
        }
        ordersMapper.updateById(orders);
        //改航程座位数(不打扰用户，更新座位)
        threadService.updateSeatCount(flightMapper,flight);
        return Result.success(null);
    }

    @Autowired
    private AirplaneMapper airplaneMapper;

    // 预订机票
    @Override
    public Result reserve(Orders orders) {
        if(orders == null){
            return Result.fail(ErrorCode.ERROR_ORDERS_RESERVE.getCode(), ErrorCode.ERROR_ORDERS_RESERVE.getMsg());
        }
        // 查询航班信息及飞机信息
        Flight flight = flightMapper.selectById(orders.getFid());
        Airplane airplane = airplaneMapper.selectById(flight.getAid());
        String rules ;
        //判断舱位
        String seatType = orders.getSeatType();
        //更新航程的座位数量
        if("Y".equals(seatType)){
            rules = airplane.getRulesY();
            threadService.updateSeatYCount(flightMapper,orders.getFid(),flight.getSeatYVacant());
        }else{
            rules = airplane.getRulesF();
            threadService.updateSeatFCount(flightMapper,orders.getFid(),flight.getSeatFVacant());
        }
        //存进数据orders
        orders.setCreateTime(new Date());
        orders.setRefund(2);
        int result = ordersMapper.insert(orders);
        if(result == 0){
            return Result.fail(ErrorCode.ERROR_ORDERS_RESERVE.getCode(), ErrorCode.ERROR_ORDERS_RESERVE.getMsg());
        }
        threadService.updateSeatNumber(ordersMapper, orders.getOid(), orders.getFid(), rules);
        return Result.success(orders);
    }

//    /**
//     *创建一个方法MathRandom
//     *产生10~50的随机整数
//     *起始i=10
//     *结束j=50
//     */
//    public static int MathRandom(int i,int j){
//        //产生一个1到5的随机整数
//        int s=i+(int)((j-i)*Math.random());
//        return s;														//返回（不可缺少）
//    }
//
//    //随机产生座位
//
//    /**
//     * public static void main(String[] args) {
//     *         String str = "34-D";
//     *         String[] split = str.split("-");
//     *         String st1=split[0];
//     *         String st2=split[1];
//     *         System.out.println(st1+st2);
//     *
//     *         int i=Integer.parseInt(st1);
//     *
//     *         int j=st2.charAt(0);
//     *
//     *         System.out.println(i);
//     *         System.out.println(j);
//     *     }
//     * @param fid
//     * @param rules == 34-D
//     * @return
//     */
//    @Override
//    public String getSeatNumber(Integer fid,String rules){
//        String[] split = rules.split("-");
//        // 34
//        int i=Integer.parseInt(split[0]);
//        // D 的split[1]
//        int j=split[1].charAt(0);
//        String seatNumber =(String)(String.valueOf(MathRandom(0,i)))+"-"+((char)MathRandom('A',j));
//        //看是否被占
//        boolean result = true;
//        while (result){
//            LambdaQueryWrapper<Orders> queryWrapper = new LambdaQueryWrapper<>();
//            queryWrapper.eq(Orders::getFid,fid);
//            queryWrapper.eq(Orders::getSeatNumber,seatNumber);
//            queryWrapper.last("limit 1");
//            Integer count = ordersMapper.selectCount(queryWrapper);
//            if(count != 0){
//                seatNumber =(String)(String.valueOf(MathRandom(0,i)))+"-"+((char)MathRandom('A',j));
//            }else{
//                result = false;
//            }
//            System.out.println(seatNumber+"-------------"+count);
//        }
//        return seatNumber;
//    }

    @Override
    public Result ticketChange(Orders orders) {
        Integer oid = orders.getOid();
        //分析
        /**
         * 改签
         * 1.将原的订单的refund 改为1
         * 2.重新预定所选的fid
         */
        if(oid != null){
            UpdateWrapper<Orders> updateWrapper = new UpdateWrapper<>();
            updateWrapper.set("refund",1).eq("oid",oid);
            int result = ordersMapper.update(null, updateWrapper);
            if(result == 0){
                return Result.fail(ErrorCode.SESSION_TIME_OUT.getCode(), ErrorCode.SESSION_TIME_OUT.getMsg());
            }
        }
        Orders firstOrder = ordersMapper.selectById(oid);
        //补充个人信息
        orders.setPassagerName(firstOrder.getPassagerName());
        orders.setPassagerCardId(firstOrder.getPassagerCardId());
        orders.setPassagerPhone(firstOrder.getPassagerPhone());
        //差额："+margin "

        int margin = firstOrder.getPrice()-orders.getPrice();
        orders.setMargin(margin);
        if(margin >=0){
            orders.setInformation("退回差额："+margin);
        }else{
            orders.setInformation("补差额："+margin);
        }
        orders.setOid(null);
        Result reserve = reserve(orders);
        return reserve;
    }

    /**
     * 付款成功
     * 1.修改refund 为 0（各个订单）
     * @param oids
     * @return
     */
    @Override
    public Result confirming(Integer[] oids,double[] prices) {
        int j = 0;
        for (Integer oid : oids) {
            UpdateWrapper<Orders> updateWrapper = new UpdateWrapper<>();
            updateWrapper.set("refund",0).set("confirming_time",new Date()).set("price",prices[j]).eq("oid", oid).last("limit 1");
            int update = ordersMapper.update(null, updateWrapper);
            if(update == 0){
                return Result.fail(ErrorCode.ERROR_ORDERS_UPDATED.getCode(), ErrorCode.ERROR_ORDERS_UPDATED.getMsg());
            }
            j++;
        }
        return Result.success(null);
    }

    @Override
    public Result getSeatByFid(Integer fid,String seatType) {
        //获取seatNumber
        List<String> seatNumberList = ordersMapper.selectSeatNumberListByFid(fid,seatType);
        for (String s : seatNumberList) {
            System.out.println(s);
        }
        //根据fid查aid得rules
        Integer aid = flightMapper.selectAidById(fid);
        Airplane airplane = airplaneMapper.selectById(aid);
        //商务舱
        String[] split = airplane.getRulesF().split("-");
        int x = Integer.parseInt(split[0]);
        int y = split[1].charAt(0)-64;
        System.out.println(x+"--"+y);
        int [][] seatFNumbers = new int[y][x];
        //经济舱
        split = airplane.getRulesY().split("-");
        x = Integer.parseInt(split[0]);
        y = split[1].charAt(0)-64;
        int [][] seatYNumbers = new int[y][x];
        //判断为什么舱位
        if(seatType == "F"){
            for (String seatNumber : seatNumberList) {
                split = seatNumber.split("-");
                x = Integer.parseInt(split[0])-1;
                y = split[1].charAt(0)-64-1;
                seatFNumbers[y][x]=1;
            }
        }else{
            for (String seatNumber : seatNumberList) {
                split = seatNumber.split("-");
                x = Integer.parseInt(split[0])-1;
                y = split[1].charAt(0)-64-1;
                seatYNumbers[y][x]=1;
            }
        }
//        for (int i = 0; i <seatFNumbers.length; i++) {
//            for (int j = 0; j < seatFNumbers[i].length; j++) {
//                System.out.print(seatFNumbers[i][j]+",");
//            }
//            System.out.println();
//        }
//        for (int i = 0; i <seatYNumbers.length; i++) {
//            for (int j = 0; j < seatYNumbers[i].length; j++) {
//                System.out.print(seatYNumbers[i][j]+",");
//            }
//            System.out.println();
//        }
        List<int[][]> list = new ArrayList<int[][]>();
        list.add(seatFNumbers);
        list.add(seatYNumbers);
//        System.out.println(list.get(1).length);
//        System.out.println("------------------------------");
//        SeatNumberVo seatNumberVo = new SeatNumberVo(seatFNumbers, seatFNumbers);
//        System.out.println(seatNumberVo.toString());
        return Result.success(list);
    }

    /**
     * 换座
     * @param oid
     * @param seatNumber
     * @return
     */
    @Override
    public Result setSeatNumber(Integer oid, String seatNumber) {
        Integer fid = ordersMapper.selectFidByOid(oid);
        LambdaQueryWrapper<Orders> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Orders::getFid,fid);
        queryWrapper.eq(Orders::getSeatNumber,seatNumber);
        Orders orders = ordersMapper.selectOne(queryWrapper);
        if(orders != null){
            return Result.fail(ErrorCode.ERROR_ORDERS_UPGRADE.getCode(), ErrorCode.ERROR_ORDERS_UPGRADE.getMsg());
        }
        UpdateWrapper<Orders> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("oid", oid);
        updateWrapper.set("seat_number",seatNumber);
        ordersMapper.update(null, updateWrapper);
        return Result.success(null);
    }

    @Override
    public Result getRefundedOrders(Integer uid) {
        List<OrdersVo> ordersVoList = ordersMapper.selectAllOrderAndFlightByUid(1,0);
        return Result.success(ordersVoList);
    }

    @Override
    public Result getRefundingOrders(Integer uid) {
        List<OrdersVo> ordersVoList = ordersMapper.selectAllOrderAndFlightByUid(1,2);
        return Result.success(ordersVoList);
    }

    public static void main(String[] args) {
//        String rules ="34-D";
//        String[] split = rules.split("-");
//        // 34
//        int i=Integer.parseInt(split[0]);
//        // D 的split[1]
//        int j=split[1].charAt(0);
//        String seatNumber =(String)(String.valueOf(MathRandom(0,i)))+"-"+((char)MathRandom('A',j));
//        System.out.println(seatNumber);
//        getSeatNumber(1,"3-A");
    }

    //转型
    private List<OrdersVo> copyList(List<Orders> records) {
        List<OrdersVo> ordersVoList=new ArrayList<>();
        for (Orders record:records) {
            ordersVoList.add(cope(record));
        }
        return ordersVoList;
    }

    private OrdersVo cope(Orders orders){
        OrdersVo ordersVo = new OrdersVo();
        BeanUtils.copyProperties(orders,ordersVo);
        return ordersVo;
    }

    //测试 by谭 2022.11.10 15：03
    //    public Result getOrderBy(){
    //        List<OrdersVo> ordersVoList = ordersMapper.selectAllOrderAndFlightByUid(1);
    //        System.out.println(ordersVoList.get(0));
    //        return null;
    //    }

}




