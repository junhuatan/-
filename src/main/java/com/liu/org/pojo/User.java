package com.liu.org.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;

/**
 * 
 * @TableName user
 */
@TableName(value ="user")
@Data
public class User implements Serializable {
    /**
     * 用户id
     */
    @TableId(type = IdType.AUTO)
    private Integer uid;

    /**
     * 用户手机号码
     */
    private String phone;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 用户真实姓名
     */
    private String username;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 用户身份证号码
     */
    private String idNo;

    /**
     * 是否注释0-非，1-是
     */
    @TableLogic
    private Integer isDelete;



    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    public User() {}

    public User(String phone) {
        this.phone = phone;
        this.password = phone;
    }
}