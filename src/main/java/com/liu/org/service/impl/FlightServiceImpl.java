package com.liu.org.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liu.org.mapper.AirlineMapper;
import com.liu.org.mapper.AirplaneMapper;
import com.liu.org.pojo.Airplane;
import com.liu.org.pojo.Flight;
import com.liu.org.service.FlightService;
import com.liu.org.mapper.FlightMapper;
import com.liu.org.vo.FlightVo;
import com.liu.org.vo.Result;
import com.liu.org.vo.params.PageParams;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundZSetOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
* @author kiwi
* @description 针对表【flight】的数据库操作Service实现
* @createDate 2022-11-05 20:52:01
*/
@Service
public class FlightServiceImpl extends ServiceImpl<FlightMapper, Flight> implements FlightService{

    @Autowired
    private FlightMapper flightMapper;

    @Autowired
    private AirplaneMapper airplaneMapper;

//    @Override
//    public Result getFlightByAll(String source, String destination, String dateOfJourney) {
//        PageParams pageParams = new PageParams();
//        Page<Flight> page = new Page<>(pageParams.getPage(), pageParams.getPageSize());
//        //添加 基本 条件
//        LambdaQueryWrapper<Flight> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.eq(Flight::getSource, source);
//        queryWrapper.eq(Flight::getDestination, destination);
//        queryWrapper.eq(Flight::getDateOfJourney, dateOfJourney);
//        //判断何种优先
//
////        queryWrapper.last("limit 1");
//        IPage<Flight> flightPage = flightMapper.selectPage(page, queryWrapper);
//        List<Flight> flights = flightPage.getRecords();
//        System.out.println(flights.size());
//        for (Flight flight : flights) {
//            System.out.println(flight);
////            System.out.println(flight.getId()+"-------"+flight.getAirline()+"-------"+flight.getDateOfJourney().getDate());
//        }
//
//        return null;
//    }

    public void updateDB(){
//        String[] d = {"东方航空","厦门航空","中国国航","海航旗下首都航","四川航空","国际航空"};
//        String[] a = {"Air India","IndiGo","Jet Airways","SpiceJet","Vistara","Multiple carriers"};
//
//        for (int i = 0; i < d.length; i++) {
//            UpdateWrapper<Flight> updateWrapper = new UpdateWrapper<>();
//            updateWrapper.set("Airline",d[i]).eq("Airline",a[i]);
//            flightMapper.update(null,updateWrapper);
//        }


//        UpdateWrapper<Flight> updateWrapper = new UpdateWrapper<>();
//        updateWrapper.set("Source",d).eq("Source",a);
//        flightMapper.update(null,updateWrapper);
//
        UpdateWrapper<Flight> updateWrapper2 = new UpdateWrapper<>();
        updateWrapper2.set("Date_of_Journey","2022-12-12").eq("Date_of_Journey","2019-04-21");
        flightMapper.update(null,updateWrapper2);
//
        UpdateWrapper<Flight> updateWrapper3 = new UpdateWrapper<>();
        updateWrapper3.set("Additional_Info","双12，全国游！！！").set("holiday_discounts",0.1).eq("Date_of_Journey","2022-12-12");
        flightMapper.update(null,updateWrapper3);
    }

    @Override
    public Result listFlight(PageParams pageParams) {
        //分页获取
        Page<Flight> page = new Page<>(pageParams.getPage(), pageParams.getPageSize());
        //添加 基本 条件
        LambdaQueryWrapper<Flight> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Flight::getSource, pageParams.getSource());
        queryWrapper.eq(Flight::getDestination, pageParams.getDestination());
        queryWrapper.eq(Flight::getDateOfJourney, pageParams.getDateOfJourney());
        //是否选择航运公司
        if(!StringUtils.isBlank(pageParams.getAirline())){
            queryWrapper.eq(Flight::getAirline, pageParams.getAirline());
        }
        //是否选择舱位
        if(!StringUtils.isBlank(pageParams.getSpace())){
            queryWrapper.gt(Flight::getSeatFVacant, 0);//商务舱座位数大于0
        }
        //判断何种优先
        //排列条件
//        if(pageParams.getCondition()==null){
//            queryWrapper.orderByAsc(Flight::getEconmecyPrice);
//        }
        System.out.println("----------------------------------------");
        System.out.println(pageParams.getCondition());
        if(pageParams.getCondition().equals("s")){
            queryWrapper.orderByAsc(Flight::getEconmecyPrice);
        }
        System.out.println("----------------------------------------");

        switch (pageParams.getCondition()){
            //低价优先
            case "is_lowPriority":
                if(StringUtils.isBlank(pageParams.getAirline()) || "c".equals(pageParams.getSpace())){
                    queryWrapper.orderByAsc(Flight::getEconmecyPrice);
                }else{
                    queryWrapper.orderByAsc(Flight::getClassPrice);
                }
                break;
            case "is_noLowPriority":
                if(StringUtils.isBlank(pageParams.getAirline()) || "f".equals(pageParams.getSpace())){
                    queryWrapper.orderByDesc(Flight::getEconmecyPrice);
                }else{
                    queryWrapper.orderByDesc(Flight::getClassPrice);
                }
                break;
            //起飞早晚
            case "is_low_departureTime":
                queryWrapper.orderByAsc(Flight::getDepTime);
                break;
            case "is_noLow_departureTime":
                queryWrapper.orderByDesc(Flight::getDepTime);
                break;
            //耗时短长
            case "is_lowDuration":
                queryWrapper.orderByAsc(Flight::getDuration);
                break;
            case "is_noLowDuration":
                queryWrapper.orderByDesc(Flight::getDuration);
                break;
            //到达早晚
            case "is_lowArrivalTime":
                queryWrapper.orderByAsc(Flight::getArrivalTime);
                break;
            case "is_noLowArrivalTime":
                queryWrapper.orderByDesc(Flight::getArrivalTime);
                break;
        }
        IPage<Flight> flightIPage = flightMapper.selectPage(page,queryWrapper);
        List<Flight> flights = flightIPage.getRecords();
        return Result.success(copyList(flights));
    }

    public  void addAid(){
        List<Flight> flights = flightMapper.selectList(new QueryWrapper<>());
        for (Flight flight : flights) {
//        Flight flight = flights.get(0);
            Integer aid = MathRandom();
            Airplane airplane = airplaneMapper.selectById(aid);
            flight.setAid(aid);
            flight.setFlightInfoId(airplane.getFlightType());
            flight.setTotalSeat(airplane.getSeatF()+airplane.getSeatY());
            flight.setSeatFVacant(airplane.getSeatF());
            flight.setSeatYVacant(airplane.getSeatY());
            int i = flightMapper.updateById(flight);
            System.out.println(i);
        }
    }

    //开始Redis缓存
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private AirlineMapper airlineMapper;

    @Override
    public Result listFlightByAirlineName(PageParams pageParams) {
        //查询多程打折情况
        HashMap<String,Double> hashMap = new HashMap<>();
        if(pageParams.getAirlineName() != null){
            for (String airlineName : pageParams.getAirlineName()) {
                hashMap.put(airlineName,airlineMapper.selectDiscountByAirlineName(airlineName));
            }
        }
        String redisName = pageParams.getDateOfJourney()+"_"+pageParams.getSource()+"_"+pageParams.getDestination()+"_"+pageParams.getSpace();
        BoundZSetOperations flightZset = redisTemplate.boundZSetOps(redisName);
        redisTemplate.expire(redisName,10, TimeUnit.MINUTES);
        //不是第一次查
        if(flightZset.size() != 0){
            Set set = flightZset.reverseRange(pageParams.getPageSize() * (pageParams.getPage() - 1), pageParams.getPageSize() * pageParams.getPage());
            List<Flight> flights = new ArrayList<Flight>(set);
            return Result.success(flights);
        }
        //第一次
        //添加 基本 条件
        LambdaQueryWrapper<Flight> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Flight::getSource, pageParams.getSource());
        queryWrapper.eq(Flight::getDestination, pageParams.getDestination());
        queryWrapper.eq(Flight::getDateOfJourney, pageParams.getDateOfJourney());
        //是否选择航运公司
        if(!StringUtils.isBlank(pageParams.getAirline())){
            queryWrapper.eq(Flight::getAirline, pageParams.getAirline());
        }
        //是否选择舱位
        if(!StringUtils.isBlank(pageParams.getSpace())){
            queryWrapper.gt(Flight::getSeatFVacant, 0);//商务舱座位数大于0
        }
        //判断何种优先
        //排列条件
        if(StringUtils.isBlank(pageParams.getAirline()) || "Y".equals(pageParams.getSpace())){
            queryWrapper.orderByAsc(Flight::getEconmecyPrice);
        }else{
            queryWrapper.orderByAsc(Flight::getClassPrice);
        }
        List<Flight> flights = flightMapper.selectList(queryWrapper);
        if(flights.size() == 0){
            return Result.success(null);
        }
        //判断是否为二次选择该公司
        if(pageParams.getAirlineName() != null){
            for (Flight flight : flights) {
                if(pageParams.getAirlineName().contains(flight.getAirline())){
                    flight.setDiscount(hashMap.get(flight.getAirline()));
                }
            }
        }
        //加入Redis
        if("F".equals(pageParams.getSpace())){
            addFInRedis(flights,flightZset);
        }else{
            addYInRedis(flights,flightZset);
        }
        System.out.println(flightZset.reverseRange(0, -1));
        System.out.println(pageParams.getPageSize() * (pageParams.getPage() + 1)+"----"+ pageParams.getPageSize() * pageParams.getPage());
        Set set = flightZset.range(pageParams.getPageSize() * (pageParams.getPage() - 1), pageParams.getPageSize() * pageParams.getPage());

        System.out.println("--------------------------");
        Iterator iterator = set.iterator();
        System.out.println(iterator);
        System.out.println(iterator.next());
        System.out.println(iterator.next());
        System.out.println(iterator.next());
        System.out.println("--------------------------");
        //        flights = new ArrayList<Flight>(set);
//        System.out.println(flights);
//        Long totalPage = flightZset.size()/pageParams.getPageSize();
        return Result.success(set);

    }

    //经济舱
    private void addYInRedis(List<Flight> flights,BoundZSetOperations flightZset){

        for (Flight flight : flights) {
            flightZset.add(flight,flight.getEconmecyPrice()*(1-flight.getDiscount()-flight.getHolidayDiscounts()));
        }
    }
    //商务舱
    private void addFInRedis(List<Flight> flights,BoundZSetOperations flightZset){
        for (Flight flight : flights) {
            flightZset.add(flight,flight.getClassPrice()*(1-flight.getDiscount()-flight.getHolidayDiscounts()));
        }
    }



    //转型
    private List<FlightVo> copyList(List<Flight> records,Long totalPage) {
        List<FlightVo> flightVoList=new ArrayList<>();
        for (Flight record:records) {
            flightVoList.add(cope(record));
        }
        if(flightVoList.size() != 0){
            flightVoList.get(0).setTotalPage(totalPage);
        }
        return flightVoList;
    }

    private List<FlightVo> copyList(List<Flight> records) {
        List<FlightVo> flightVoList=new ArrayList<>();
        for (Flight record:records) {
            flightVoList.add(cope(record));
        }

        return flightVoList;
    }

    private FlightVo cope(Flight flight){
        FlightVo flightVo = new FlightVo();
        BeanUtils.copyProperties(flight,flightVo);
        return flightVo;

    }

    /**
     *创建一个方法way_0
     *产生10~50的随机整数
     *起始num_0=10
     *结束num_1=50
     */
    public static int MathRandom(){
        //产生一个1到5的随机整数
        int s=1+(int)(5*Math.random());
        return s;														//返回（不可缺少）
    }

    public static void main(String[] args) {
        for (int i = 0; i < 50; i++) {
            Integer aid = MathRandom();
            System.out.println(aid);
        }
    }
}




