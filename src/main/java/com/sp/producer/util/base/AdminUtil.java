//package com.sp.producer.util.base;
//
//import kafka.admin.AdminUtils;
//import kafka.utils.ZkUtils;
//import org.apache.kafka.common.security.JaasUtils;
//
//import java.util.Properties;
//
///**
// *
// * 功能说明:自定义创建主题类<p>
// *    <ul>注:
// *      <li>1.Kafka将zookeeper的操作封装成一个ZkUtils类,通过AdminUtils类来调用ZkUtils</li>
// *      <li>2.使用该工具类创建主题,并同时创建指定大小的分区数</li>
// *    </ul>
// *
// * @author yushu.zhao
// * @create 2020-12-29 13:40
// */
//public class AdminUtil {
//
//    //zookeeper连接配置
//    public static final String ZK_CONNECT = "192.170.15.29:2181";
//
//    //session过期时间
//    public static final int SEESSION_TIMEOUT = 30 * 1000;
//
//    //连接超时时间
//    public static final int CONNECT_TIMEOUT = 30 * 1000;
//
//
//    /**
//     * 创建主题
//     *
//     * @param topic 主题名称
//     * @param partition 分区数
//     * @param repilcas  副本数
//     * @param properties 配置信息
//     */
//    public static void createTopic(String topic, int partition, int repilcas, Properties properties){
//
//        ZkUtils zkUtils = null;
//
//        try {
//            //创建zkUtils
//            zkUtils = ZkUtils.apply(ZK_CONNECT,SEESSION_TIMEOUT,CONNECT_TIMEOUT, JaasUtils.isZkSecurityEnabled());
//
//            if (!AdminUtils.topicExists(zkUtils,topic)){
//                //主题不存在,则创建主题
//                AdminUtils.createTopic(zkUtils, topic, partition, repilcas, properties, AdminUtils.createTopic$default$6());
//            }
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }finally {
//            zkUtils.close();
//        }
//
//    }
//
//    /**
//     * 删除主题
//     *
//     * @param topic 主题名称
//     */
//    public static void deleteTopic(String topic){
//
//        ZkUtils zkUtils = null;
//
//        try {
//            zkUtils = ZkUtils.apply(ZK_CONNECT, SEESSION_TIMEOUT, CONNECT_TIMEOUT, JaasUtils.isZkSecurityEnabled());
//            AdminUtils.deleteTopic(zkUtils,topic);
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }finally {
//            zkUtils.close();
//        }
//
//    }
//
//
//
//
//
//
//
//
//
//
//}
