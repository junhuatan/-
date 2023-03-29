package com.liu.org.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.liu.org.pojo.Flight;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* @author kiwi
* @description 针对表【flight】的数据库操作Mapper
* @createDate 2022-11-05 20:52:01
* @Entity com.liu.org.pojo.Flight
*/
@Repository
public interface FlightMapper extends BaseMapper<Flight> {

    @Select("select id,Destination,Airline,Date_of_Journey,flight_info_id,Source,Route,Dep_Time,Arrival_Time,Duration,Additional_Info from flight where id = #{fid}")
    Flight selectOrdersFlightByFid(Integer fid);

    Integer selectSeatFVacantById(Integer fid);

    Integer selectSeatYVacantById(Integer fid);

    @Select("select * from flight where id = #{fid}")
    List<Flight> selectDouList(Wrapper<Flight> queryWrapper);

    @Select("select aid from flight where id = #{fid} limit 1")
    Integer selectAidById(Integer fid);


}




