package com.liu.org.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.liu.org.pojo.Flight;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Date;

@Data
public class OrdersVo {

    private Integer oid; // 订单id
    private String passagerName;    // 乘客姓名
    private String seatNumber;  // 座位信息
    private String seatType;    // 座位类型
    /**
     * 信息
     */
    private String information;
    private Integer price;  // 价格


    private Timestamp createTime;   // 创建时间

    /**
     * 付款时间
     */
    private Date confirmingTime;

    private Flight flight;  // 航班信息
}
