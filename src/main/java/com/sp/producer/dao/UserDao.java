package com.sp.producer.dao;

import com.sp.producer.entity.User;
import com.sp.producer.util.base.Page;


public interface UserDao {

    int insert(User roncooUser);

    int deleteById(int id);

    int updateById(User roncooUser);

    User selectById(int id);

    Page<User> queryForPage(int i, int j, String string);
}
