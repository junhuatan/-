package com.liu.org.vo;

import lombok.Data;

import java.util.Date;

@Data
public class FlightVo {

    //id
    private Integer id;

    //航运公司
    private String airline;

    //起飞日期
    private Date dateOfJourney;

    //飞机名字
    private String flightInfoId;

    //初始地
    private String source;

    //到达地
    private String destination;

    //经过
    private String route;

    //出发时间
    private String depTime;

    //到达时间
    private String arrivalTime;

    //费时间
    private String duration;

//    private String totalStops;

    //通告
    private String additionalInfo;

    //多程打折
    private Double discount =0.0;

    //节假日打折
    private Double holidayDiscounts;

    //经济舱费用
    private Double econmecyPrice;

    //商务舱费用
    private Integer classPrice;

    //总座位数
    private Integer totalSeat;

    //商务
    private Integer seatFVacant;

    //经济
    private Integer seatYVacant;

    //总页数
    private Long totalPage;
}
