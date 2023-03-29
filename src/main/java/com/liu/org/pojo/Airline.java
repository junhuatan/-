package com.liu.org.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName airline
 */
@TableName(value ="airline")
@Data
public class Airline implements Serializable {
    /**
     * 飞机所属公司id
     */
    @TableId(type = IdType.AUTO)
    private Integer cid;

    /**
     * 飞机所属公司
     */
    private String airlineName;

    /**
     * 公司多程优惠
     */
    private Double discount;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}