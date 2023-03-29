package com.liu.org.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName airplane
 */
@TableName(value ="airplane")
@Data
public class Airplane implements Serializable {
    /**
     * 记录编号
     */
    @TableId(type = IdType.AUTO)
    private Integer aid;

    /**
     * 飞机类型，如空客A319
     */
    private String flightType;

    /**
     * 头等舱座位数量
     */
    private Integer seatF;

    /**
     * 经济舱座位数量
     */
    private Integer seatY;

    /**
     * 座位编号规则，主要指定列为多少，编号是什么，如列为A、B、C、D，编号为列号+行号，则第二行第一个位置为A2
     */
    private String rulesF;

    /**
     * 座位编号规则，主要指定列为多少，编号是什么，如列为ABCDEF，编号为列号+行号，则第二行第而个位置为B2
     */
    private String rulesY;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}