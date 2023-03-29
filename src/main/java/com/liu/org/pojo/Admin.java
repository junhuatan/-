package com.liu.org.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 *
 * @TableName admin
 */
@TableName(value ="admin")
@Data
public class Admin implements Serializable {
    /**
     * 管理员id
     */
    @TableId(type = IdType.AUTO)
    private Integer aId;

    /**
     * 管理员密码
     */
    private String password;

    /**
     * 管理员名称
     */
    private String adminNane;

    /**
     * 管理眼联系方式
     */
    private String aPhone;

    /**
     * 管理员在用户表中的id
     */
    private Integer uId;

    /**
     * 修改时间
     */
    private Date modificationTime;

    /**
     * 修改人a_id
     */
    private Integer modificationId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}