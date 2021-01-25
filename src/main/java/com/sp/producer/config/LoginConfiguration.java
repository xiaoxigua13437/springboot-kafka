package com.sp.producer.config;

import com.sp.producer.interceptor.AccessLimitInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


/**
 * 自定义分布式限流拦截器
 *
 * @author yushu
 * @create 2020-08-21 14:54
 */
@Configuration
public class LoginConfiguration extends WebMvcConfigurerAdapter {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        //拦截对所有来自这个请求链接
        registry.addInterceptor(new AccessLimitInterceptor()).addPathPatterns("/kafka/test");

        super.addInterceptors(registry);
    }
}
