package com.sp.producer.interceptor;


import com.sp.producer.util.CheckRequestTypeUtil;
import com.sp.producer.util.RedisCommonUtil;
import com.sp.producer.util.SpringContextHolder;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * 自定义注解+拦截器+Redis实现限流 (单体和分布式均适用,全局限流):<br>
 *
 * @author yushu.zhao
 * @create 2020-12-17 09:07
 *
 */

public class AccessLimitInterceptor implements HandlerInterceptor {


    private RedisCommonUtil redisCommonUtil = SpringContextHolder.getBean(RedisCommonUtil.class);



    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            if (!method.isAnnotationPresent(AccessLimit.class)) {
                return true;
            }
            //获取自定义注解类中的参数值
            AccessLimit accessLimit = method.getAnnotation(AccessLimit.class);
            if (accessLimit == null) {
                return true;
            }
            int count = accessLimit.count();
            long time = accessLimit.time();
            String key = CheckRequestTypeUtil.getClientIp(request) + request.getRequestURI();
            Integer maxLimit = (Integer) redisCommonUtil.get(key);
            if (maxLimit == null) {
                redisCommonUtil.setEx(key,1,time);

            } else if (maxLimit < count) {
                redisCommonUtil.setEx(key,maxLimit + 1,time);
            } else {
                output(response, "请求太频繁!");
                return false;
            }
        }
        return true;

    }


    public void output(HttpServletResponse response, String msg) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        ServletOutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            outputStream.write(msg.getBytes("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            outputStream.flush();
            outputStream.close();
        }
    }


}
