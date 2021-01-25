package com.sp.producer.interceptor;

import java.lang.annotation.*;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;


/**
 * 自定义注解类
 *
 * @author yushu.zhao
 * @create 2020-12-17 10:13
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
@Order(Ordered.HIGHEST_PRECEDENCE)
public @interface AccessLimit {

    /**
     * 允许访问的最大次数
     */
    int count() default Integer.MAX_VALUE;

    /**
     * 时间段，单位为毫秒，默认值一分钟
     */
    long time() default 60000;
}
