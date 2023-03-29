package com.liu.org.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
//它是lombok中的注解,作用在类上;
//使用后添加一个构造函数，该构造函数含有所有已声明字段属性参数
//对应的 @NoArgsConstructor 是添加一个无参数的构造器，一般使用@Builder注解时同时会使用@AllArgsConstructor和@NoArgsConstructor
public class Result {

    private boolean success;

    private int code;

    private String msg;

    private Object data;

    public static Result success(Object data){
        return new Result(true,200,"success",data);
    }

    public static Result fail(int code,String msg){
        return new Result(false,code,msg,null);
    }
}
