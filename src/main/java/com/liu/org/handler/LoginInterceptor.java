package com.liu.org.handler;

import com.alibaba.fastjson.JSON;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.liu.org.Utils.UserThreadLocal;
import com.liu.org.pojo.User;
import com.liu.org.service.UserService;
import com.liu.org.vo.ErrorCode;
import com.liu.org.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {


    @Autowired
    private UserService loginService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //在执行controller方法(Handler)之前进行执行
        System.out.println("1我被拦了！！！！！！！！！！！！！！！！！！！！");
        /**
         * 1. 需要判断 请求的接口路径 是否为 HandlerMethod (controller方法)
         * 2. 判断 token是否为空，如果为空 未登录
         * 3. 如果token 不为空，登录验证 loginService checkToken
         * 4. 如果认证成功 放行即可
         */
        if(!(handler instanceof HandlerMethod)){
            //handler 可能是RequestResourceHandler spring boot 程序的 访问静态资源 默认去classpath 下的static目录去查询、
            System.out.println("566666");
            return true;
        }
//        "Authorization": "eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2NjgxNTY5MjYsInVzZXJJZCI6MSwiaWF0IjoxNjY4MDcwNTI2fQ.VhtPrfxo9gpmHbHG5QlGmW6YrmiHBGyVUTzteo7DyhA"

        String token = request.getHeader("Authorization");

        log.info("=================request start===========================");
        String requestURI = request.getRequestURI();
        log.info("request uri:{}",requestURI);
        log.info("request method:{}",request.getMethod());
        log.info("token:{}", token);
        log.info("=================request end===========================");

        if(StringUtils.isBlank(token)){
            Result result = Result.fail(ErrorCode.NO_LOGIN.getCode(), "未登录");
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().print(JSON.toJSONString(result));
            System.out.println("no1-------");
            return false;
        }
        User user = loginService.checkToken(token);
        if(user == null){
            Result result = Result.fail(ErrorCode.NO_LOGIN.getCode(), "未登录");
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().print(JSON.toJSONString(result));
            System.out.println("no2-------");
            return false;
        }
        //登录验证成功，放行
        //我希望在controller中 直接获取用户的信息 怎么获取?
        UserThreadLocal.put(user);
        System.out.println("yes------");
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //如果不删除 ThreadLocal中用完的信息 会有内存泄漏的风险
        UserThreadLocal.remove();
    }
}
