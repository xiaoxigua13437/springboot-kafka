package com.sp.producer.dao.mybatis;

import com.sp.producer.entity.User;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface UserXmlMapper {

    List<User> select();

}
