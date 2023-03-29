package com.liu.org.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @TableName orders
 */
@TableName(value ="orders")
@Data
public class Orders implements Serializable {
    /**
     * 订单id
     */
    @TableId(type = IdType.AUTO)
    private Integer oid;

    /**
     * 订票人信息：用于查询该订单的乘机人
     */
    private Integer uid;

    /**
     * 航班id
     */
    private Integer fid;

    /**
     * 乘客姓名
     */
    private String passagerName;

    /**
     * 乘客身份证
     */
    private String passagerCardId;

    /**
     * 乘客电话号码
     */
    private String passagerPhone;

    /**
     * 座位信息
     */
    private String seatNumber;

    /**
     * 座位类型
     */
    private String seatType;

    /**
     * 价格
     */
    private Integer price;

    /**
     * 信息
     */
    private String information;

    /**
     * 是否退订：0-完成订单 1-退订 2-未支付
     */
    private Integer refund;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 付款时间
     */
    private Date confirmingTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @TableField(exist = false)
    private Flight flight;

    @TableField(exist = false)
    private Integer margin;
}