package com.liu.org.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.liu.org.mapper.FlightMapper;
import com.liu.org.mapper.OrdersMapper;
import com.liu.org.pojo.Flight;
import com.liu.org.pojo.Orders;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class ThreadService {

    //更新flight seat数量
    //期望其操作在线程池中 执行 不会影响原有的主线程
    @Async("taskExecutor")
    public void updateSeatCount(FlightMapper flightMapper, Flight flight) {
        UpdateWrapper<Flight> updateWrapperF = new UpdateWrapper<>();
        UpdateWrapper<Flight> updateWrapperY = new UpdateWrapper<>();
        boolean result= true;
        int count = flight.getSeatFVacant();
        while(result){
            count -=1;
            updateWrapperF.set("seat_f_vacant", count).eq("id", flight.getId()).eq("seat_f_vacant", count + 1);
            int update = flightMapper.update(null, updateWrapperF);
            if(update == 1){
                result=false;
            }
        }
        count = flight.getSeatYVacant();
        while(!result){
            count +=1;
            updateWrapperY.set("seat_y_vacant", count).eq("id", flight.getId()).eq("seat_y_vacant", count - 1);
            int update = flightMapper.update(null, updateWrapperY);
            if(update == 1){
                result=true;
            }
        }
        try {
            Thread.sleep(5000);
            System.out.println("更新完成了.....");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    //更新Y_count
    @Async("taskExecutor")
    public void updateSeatYCount(FlightMapper flightMapper, Integer fid, Integer seatCount) {
        boolean result= true;
        while(result){
            UpdateWrapper<Flight> updateWrapperY = new UpdateWrapper<>();
            seatCount -=1;
            updateWrapperY.set("seat_y_vacant", seatCount).eq("id", fid).eq("seat_y_vacant", seatCount + 1);
            int update = flightMapper.update(null, updateWrapperY);
            if(update == 1){
                result=false;
            }
        }
        try {
            Thread.sleep(5000);
            System.out.println("经济舱数量更新完成了.....");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    //更新F_count
    @Async("taskExecutor")
    public void updateSeatFCount(FlightMapper flightMapper, Integer fid, Integer seatCount) {
        boolean result= true;
        while(result){
            seatCount -=1;
            UpdateWrapper<Flight> updateWrapperF = new UpdateWrapper<>();
            updateWrapperF.set("seat_f_vacant", seatCount).eq("id", fid).eq("seat_f_vacant", seatCount + 1);
            int update = flightMapper.update(null, updateWrapperF);
            if(update == 1){
                result=false;
            }
        }
        try {
            Thread.sleep(5000);
            System.out.println("商务舱数量更新完成了.....");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    //orders加座位
    //期望其操作在线程池中 执行 不会影响原有的主线程
    @Async("taskExecutor")
    public void updateSeatNumber(OrdersMapper ordersMapper,Integer oid , Integer fid, String rules ) {
        //产生座位号
        String seatNumber = this.getSeatNumber(ordersMapper, fid, rules);
        UpdateWrapper<Orders> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("seat_number", seatNumber).eq("oid", oid);
        ordersMapper.update(null,updateWrapper);
        try {
            Thread.sleep(5000);
            System.out.println("分配座位完成了.....");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    /**
     *创建一个方法MathRandom
     *产生10~50的随机整数
     *起始i=10
     *结束j=50
     */
    public static int MathRandom(int i,int j){
        //产生一个1到5的随机整数
        int s=i+(int)((j-i)*Math.random());
        return s;														//返回（不可缺少）
    }

    //随机产生座位
    public String getSeatNumber(OrdersMapper ordersMapper, Integer fid, String rules){
        String[] split = rules.split("-");
        // 34
        int i=Integer.parseInt(split[0]);
        // D 的split[1]
        int j=split[1].charAt(0);
        String seatNumber =(String)(String.valueOf(MathRandom(1,i)))+"-"+((char)MathRandom('A',j));
        //看是否被占
        boolean result = true;
        while (result){
            LambdaQueryWrapper<Orders> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Orders::getFid,fid);
            queryWrapper.eq(Orders::getSeatNumber,seatNumber);
            queryWrapper.last("limit 1");
            Integer count = ordersMapper.selectCount(queryWrapper);
            if(count != 0){
                seatNumber =(String)(String.valueOf(MathRandom(0,i)))+"-"+((char)MathRandom('A',j));
            }else{
                result = false;
            }
            System.out.println(seatNumber+"-------------"+count);
        }
        return seatNumber;
    }
}
