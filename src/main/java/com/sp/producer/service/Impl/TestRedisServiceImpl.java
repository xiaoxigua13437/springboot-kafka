package com.sp.producer.service.Impl;

import com.sp.producer.dao.mybatis.UserMapper;
import com.sp.producer.entity.User;
import com.sp.producer.service.TestRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class TestRedisServiceImpl implements TestRedisService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public int insert(User user) {
        return userMapper.insert(user);
    }


    @Override
    public String findUserById(Integer id) {


        return null;
    }
}
