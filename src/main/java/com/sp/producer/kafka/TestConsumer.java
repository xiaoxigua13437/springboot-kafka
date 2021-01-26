package com.sp.producer.kafka;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sp.producer.entity.User;
import com.sp.producer.service.TestRedisService;
import com.sp.producer.util.RedisCommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;


/**
 * 监听服务器上的kafka是否有相关的消息发过来：<br>
 * <ul>
 * <li>类功能描述1<br>
 * <li>类功能描述2<br>
 * <li>类功能描述3<br>
 * </ul>
 * 修改记录：<br>
 * <ul>
 * <li>修改记录描述1<br>
 * <li>修改记录描述2<br>
 * <li>修改记录描述3<br>
 * </ul>
 *
 * @author yushu.zhao
 * @version 0.1 since 2020-12-15 14:20
 */
@Component
@Slf4j
public class TestConsumer {



    @Autowired
    private RedisCommonUtil redisCommonUtil;

    @Autowired
    private TestRedisService testRedisService;


    private Gson gson = new GsonBuilder().create();



    /**
     * 定义此消费者接收topics = "demo"的消息，与controller中的topic对应上即可
     * @param record 变量代表消息本身，可以通过ConsumerRecord<?,?>类型的record变量来打印接收的消息的各种信息
     */
    /*@KafkaListener(topics = "demo")
    public void listen (ConsumerRecord<?, ?> record){
        System.out.printf("topic is %s, offset is %d, value is %s \n", record.topic(), record.offset(), record.value());
    }*/


    /*@KafkaListener(containerGroup="defaultConsumerGrouptest8",topicPartitions = {
            @TopicPartition(topic = "kafkaTest",partitions = {"0","1"}),

    })
    public void onMessage1(ConsumerRecord<?,?> record){

        *//*Optional message = Optional.ofNullable(record.value());
        Object msg = message.get();

        User user = new User();
        user.setName(msg.toString());
        user.setCreateTime(new Date());
        testRedisService.insert(user);*//*
        // 消费的哪个topic、partition的消息,打印出消息内容
        log.info("我是分区 0  1："+record.topic()+"-"+record.partition()+"-"+record.value());

    }



    //一个组里面的多个消费者 消费不同的分区
    @KafkaListener(containerGroup="defaultConsumerGrouptest8",topicPartitions = {

            @TopicPartition(topic = "kafkaTest",partitions = {"2","3"})
    })
    public void onMessages(ConsumerRecord<?, ?> record){
        *//*Optional message = Optional.ofNullable(record.value());
        Object msg = message.get();

        User user = new User();
        user.setName(msg.toString());
        user.setCreateTime(new Date());
        testRedisService.insert(user);*//*
        // 消费的哪个topic、partition的消息,打印出消息内容
        log.info("我是分区 2 3："+record.topic()+"-"+record.partition()+"-"+record.value());
    }



    //一个组里面的多个消费者 消费不同的分区
    @KafkaListener(containerGroup="defaultConsumerGrouptest8",topicPartitions = {
            @TopicPartition(topic = "kafkaTest",partitions = {"4","5"})
    })
    public void onMessages2(ConsumerRecord<?, ?> record){

        *//*Optional message = Optional.ofNullable(record.value());
        Object msg = message.get();

        User user = new User();
        user.setName(msg.toString());
        user.setCreateTime(new Date());
        testRedisService.insert(user);*//*
        // 消费的哪个topic、partition的消息,打印出消息内容
        log.info("我是分区 4 5："+record.topic()+"-"+record.partition()+"-"+record.value());
    }*/




   /* @KafkaListener(topics = TestProducer.TEST,groupId = TestProducer.TOPIC_GROUP1)
    public void topic_test(ConsumerRecord<?,?> record, Acknowledgment ack, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic){

        Optional message = Optional.ofNullable(record.value());

        *//*User user = gson.fromJson((String) record.value(),User.class);*//*

        if (message.isPresent()){
            Object msg = message.get();

            *//*User user = new User();
            user.setName(msg.toString());
            user.setCreateTime(new Date());
            testRedisService.insert(user);*//*
            log.info("topic_test 消费了： Topic:" + topic + ",Message:" + msg);
            ack.acknowledge();
        }
    }*/
    /**
     * 批量消费
     *
     * @param
     *
     */
    /*@KafkaListener(topics = TestProducer.TEST,containerFactory = "batchFactory")
    public void topic_test2(List<ConsumerRecord<String, String>> records){

        batchConsumer(records);

    }*/



    /*@KafkaListener(topics = "kafkaTest", groupId = TestProducer.TOPIC_GROUP2)
    public void topic_test1(ConsumerRecord<?, ?> record, Acknowledgment ack, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {


        Optional message = Optional.ofNullable(record.value());
        if (message.isPresent()) {
            Object msg = message.get();
            log.info("topic_test1 消费了： Topic:" + topic + ",Message:" + msg);

            ack.acknowledge();

        }


    }*/


    /**
     * 批量消费
     */
    public void batchConsumer(List<ConsumerRecord<String, String>> records) {
        records.forEach(record -> consumer(record));
    }

    /**
     * 单条消费
     */
    public void consumer(ConsumerRecord<String, String> record) {
        log.debug("主题:{}, 内容: {}", record.topic(), record.value());
    }



    /**
     * 生成唯一编码(由6位随机数+年月日时分秒组合)
     *
     * @return uniqueId
     */
    public static String getUniqueId(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 1; i <= 6; i++){
            sb.append(random.nextInt(i));
        }
        sb.append(sdf.format(new Date()));
        return sb.toString();
    }

}
