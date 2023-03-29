package com.liu.org.mapper;

import com.liu.org.pojo.Flight;
import com.liu.org.pojo.Orders;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liu.org.vo.OrdersVo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* @author kiwi
* @description 针对表【orders】的数据库操作Mapper
* @createDate 2022-11-10 11:17:31
* @Entity com.liu.org.pojo.Orders
*/
@Repository
public interface OrdersMapper extends BaseMapper<Orders> {

    // 后台查询所有订单及航班信息
    @Select("select * from orders where refund != 1 order by create_time desc")
    @Results({
            @Result(column = "oid",property = "oid"),
            @Result(column = "passager_name",property = "passagerName"),
            @Result(column = "seat_number",property = "seatNumber"),
            @Result(column = "seat_type",property = "seatType"),
            @Result(column = "price",property = "price"),
            @Result(column = "information",property = "information"),
            @Result(column = "create_time",property = "createTime"),
            @Result(column = "confirming_time",property = "confirmingTime"),
            @Result(column = "fid",property = "flight",javaType = Flight.class,
                    one = @One(select = "com.liu.org.mapper.FlightMapper.selectById")
            )
    })
    List<OrdersVo> selectAllOrderAndFlight();

    //查询 用户 所有订单及航班信息
    @Select("select * from orders where refund = #{refund} and uid = #{uid} order by create_time desc ")
    @Results({
            @Result(column = "oid",property = "oid"),
            @Result(column = "passager_name",property = "passagerName"),
            @Result(column = "seat_number",property = "seatNumber"),
            @Result(column = "seat_type",property = "seatType"),
            @Result(column = "price",property = "price"),
            @Result(column = "information",property = "information"),
            @Result(column = "create_time",property = "createTime"),
            @Result(column = "confirming_time",property = "confirmingTime"),
            @Result(column = "fid",property = "flight",javaType = Flight.class,
                    one = @One(select = "com.liu.org.mapper.FlightMapper.selectOrdersFlightByFid")
            )
    })
    List<OrdersVo> selectAllOrderAndFlightByUid(@Param("uid") Integer uid,@Param("refund") Integer refund);

    @Select("select seat_number from orders where fid = #{fid} and seat_type =#{seatType}")
    List<String> selectSeatNumberListByFid(@Param("fid") Integer fid,@Param("seatType") String seatType);

    @Select("select fid from orders where oid = #{oid} limit 1")
    Integer selectFidByOid(Integer oid);
}




