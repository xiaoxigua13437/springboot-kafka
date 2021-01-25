package com.sp.producer.controller;

import com.sp.producer.entity.User;
import com.sp.producer.service.TestRedisService;
import com.sp.producer.util.RedisCommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Date;



/**
 *
 * string数据类型
 * 应用场景： 秒杀商品/点赞
 *
 * hash数据类型
 * 实际应用场景：电商网站中的商品详细信息
 *
 * list数据类型
 * 应用场景： 商品的评论表
 *
 * set数据类型
 * 应用场景： 微信朋友查看权限 /独立IP投票限制
 *
 * zset数据类型
 * 应用场景： 商品的销售排行榜
 *
 *
 * @author yushu.zhao
 * @create 2020-12-21 11:22
 */
@RestController
public class TestRedisController {


    private static final Logger logger = LoggerFactory.getLogger(TestRedisController.class);


    @Autowired
    private RedisCommonUtil redisCommonUtil;


    @Autowired
    private TestRedisService testRedisService;


    /**
     *
     * 数据录入redis
     *
     * @param k key
     * @param v value
     * @return
     */
    @RequestMapping(value = "/addValueToRedis",method = RequestMethod.GET)
    public String addValueToRedis(@RequestParam("k")String k , @RequestParam("v")String v){
        redisCommonUtil.set(k,v);
        return (String) redisCommonUtil.get(k);
    }


    /**
     * 通过主键获取redis中的值
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/getValueByRedisId/{id}",method = RequestMethod.GET)
    public String getValueByRedisId(@PathVariable("id") Integer id){

        String userById = null;

        //第一步先判断缓存中是否存在该用户,如不存在就从数据库拿
        String userKey = (String) redisCommonUtil.get(id.toString());
        if (userKey == null){
            logger.info("开始从数据库单独提取对应ID学生信息");
            userById = testRedisService.findUserById(id);
            if (userById != null) redisCommonUtil.set(id.toString(),userById);
            else return "noUser";

            return (String) redisCommonUtil.get(id.toString());
        }
        logger.info("正在从数据库单独提取对应ID学生信息");
        return  (String) redisCommonUtil.get(id.toString());


    }


    /**
     * 测试添加用户信息
     * @return
     */
    @RequestMapping(value = "/testAdd")
    public String testAdd(){
        User user = new User();
        user.setName("maria");
        user.setCreateTime(new Date());
        return String.valueOf(testRedisService.insert(user));
    }
























}
