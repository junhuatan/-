package com.liu.org.vo.params;

import lombok.Data;

import java.util.List;

@Data
public class PageParams {

    private int page=1;

    private int pageSize=10;

    //航运公司
    private String airline;

    //发源地
    private String source;

    //目的地
    private String destination;

    //出发时间”2019-06-03“
    private String dateOfJourney;

    //所选择舱位 f为商务舱、y为经济舱
    private String space;

    //排列条件
    private String condition = "is_lowPriority";

    //前面所选的公司名称List
    private List<String> airlineName;



//    //是否低价优先
//    private boolean is_lowPriority;
//
//    //是否起飞早晚
//    private boolean is_low_departureTime;
//
//    //耗时短长
//    private boolean is_lowDuration;

}
