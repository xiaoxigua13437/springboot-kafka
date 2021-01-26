package com.sp.producer.dao.mybatis;

import com.sp.producer.entity.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

@Repository

public interface UserMapper {

    @Insert(value = "insert into user (name, create_time) values (#{name,jdbcType=VARCHAR}, #{createTime,jdbcType=TIMESTAMP})")
    int insert(User user);


    @Select(value = "select id, name, create_time from user where id = #{id,jdbcType=INTEGER}")
    @Results(value = { @Result(column = "create_time", property = "createTime", jdbcType = JdbcType.TIMESTAMP) })
    User selectByPrimaryKey(Integer id);

}