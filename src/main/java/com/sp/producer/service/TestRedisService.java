package com.sp.producer.service;

import com.sp.producer.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface TestRedisService {


    int insert(User user);


    String findUserById(Integer id);
}
