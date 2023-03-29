package com.liu.org.vo.params;

import com.baomidou.mybatisplus.annotation.TableField;
import com.liu.org.pojo.Flight;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;



@Data
public class FlightsParam implements Serializable {

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

    //所选择舱位
    private String space;

    //排列条件
    private String condition = "is_lowPriority";
}