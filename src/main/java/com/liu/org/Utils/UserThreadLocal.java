package com.liu.org.Utils;


import com.liu.org.pojo.User;

public class UserThreadLocal {

    private UserThreadLocal() {}

    //线程变量隔离
    private static final ThreadLocal<User> LOCAL = new ThreadLocal<User>();

    public static void put(User user) {
        LOCAL.set(user);
    }

    public static User get() {
        return LOCAL.get();
    }

    public static void remove() {
        LOCAL.remove();
    }
}
