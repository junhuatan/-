package com.liu.org.mapper;

import com.liu.org.pojo.Airline;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
* @author kiwi
* @description 针对表【airline】的数据库操作Mapper
* @createDate 2022-11-25 20:14:16
* @Entity com.liu.org.pojo.Airline
*/
@Repository
public interface AirlineMapper extends BaseMapper<Airline> {

    @Select("select discount from airline where airline_name = #{airlineName} ")
    Double selectDiscountByAirlineName(@Param("airlineName") String airlineName);

}




