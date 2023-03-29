package com.liu.org.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @TableName flight
 */
@TableName(value ="flight")
@Data
public class Flight implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private Integer aid;

    private String destination;

    private String airline;

    private Date dateOfJourney;

    private String flightInfoId;

    private String source;

    private String route;

    private String depTime;

    private String arrivalTime;

    private String duration;

    private String totalStops;

    private String additionalInfo;

    private Double holidayDiscounts=0.0;

    private Double econmecyPrice;

    private Integer classPrice;

    private Integer totalSeat;

    private Integer seatFVacant;

    private Integer seatYVacant;

    private static final long serialVersionUID = 1L;

    @TableField(exist = false)
    private Double discount =0.0;

}