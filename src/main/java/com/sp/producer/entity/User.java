package com.sp.producer.entity;

import lombok.Getter;
import lombok.Setter;
import java.util.Date;

/**
 * 用户实体类
 * @author yushu.zhao
 * @create  2020/2/8
 */
@Getter
@Setter
public class User {
    private int id;
    private String name;
    private Date createTime;

}
