package com.sp.producer;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching//开启缓存机制
@MapperScan("com.sp.producer.dao.mybatis")
public class ProducerApplication {

    public static void main(String[] args) {

        SpringApplication.run(ProducerApplication.class, args);
    }




}
