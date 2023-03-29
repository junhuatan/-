package com.liu.org.vo;

public enum ErrorCode {

    PARAMS_ERROR(10001,"参数有误"),
    ACCOUNT_PWD_NOT_EXIST(10002,"用户名或密码不存在"),
    ADMIN_NOT_EXIST(55555,"您的权限不够"),
    TOKEN_ERROR(10003,"token不合法"),
    ACCOUNT_EXIST(10004,"账号已存在"),
    ACCOUNT_NOCHECK(10005,"用户冲突"),
    NO_PERMISSION(70001,"无访问权限"),
    SESSION_TIME_OUT(90001,"会话超时"),
    NO_LOGIN(90002,"未登录"),
    NO_HAVE_ORDERS(80001,"没有找到订单"),
    ERROR_ORDERS_UPDATED(80002,"更新订单失败"),
    ERROR_ORDERS_UPGRADE(80003,"占位失败"),
    ERROR_ORDERS_RESERVE(80004,"预定失败")
    ;

    private int code;
    private String msg;

    ErrorCode(int code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}