package com.sp.producer.controller;

import com.sp.producer.interceptor.AccessLimit;
import com.sp.producer.kafka.TestProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 测试类
 *
 * @author yushu.zhao
 * @create 2020-01-25 14:35
 */
@RestController
@Slf4j
@RequestMapping(value = "/kafka")
public class TestKafkaController {

    @Autowired
    private TestProducer producer;

    /**
     * 发送消息到kafka
     */
    @RequestMapping(value = "/sendMsg",method = RequestMethod.GET)
    @Transactional(rollbackFor = Exception.class)
    public void sendMsg(){
        producer.sendMessageAsync("this is a test kafka topic message!");
    }



    @RequestMapping(value = "/test")
    @AccessLimit(count = 4,time = 10)
    public String test(HttpServletRequest request, @RequestParam(value = "username",required = false) String userName){

        return   "hello world !";
    }


}
