package com.sp.producer.kafka;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;


/**
 * 测试kafka生产者：<br>
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
@AllArgsConstructor
@Slf4j
@Component
public class TestProducer {

    @Autowired
    private final KafkaTemplate<String,Object> kafkaTemplate;

//    private final KafkaProducer<String,String> producer;

    //自定义topic
    public static final String TOPIC_TEST = "topic.test";

    //自定义topic
    public static final String TEST = "test";

    //自定义group1
    public static final String TOPIC_GROUP1 = "topic.group1";

    //自定义group2
    public static final String TOPIC_GROUP2 = "topic.group2";



    /**
     * 同步发送
     *
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public String syncSendMessage() {
        for (int i = 0; i < 100; i++) {
            try {
                kafkaTemplate.send("kafka-boot", "0", "foo" + i).get();
            } catch (InterruptedException e) {
                log.error("sync send message fail [{}]", e.getMessage());
                e.printStackTrace();
            } catch (ExecutionException e) {
                log.error("sync send message fail [{}]", e.getMessage());
                e.printStackTrace();
            }
        }
        return "success";
    }


    /**
     * 同步发送
     */
    /*public void syncSendMessage2(){

        ProducerRecord<String, String> record = new ProducerRecord<>("CustomCountry", "Precision Products", "France");//Topic Key Value
        try {

            for (;;){
                Future future = producer.send(record);
                future.get();//不关心是否发送成功，则不需要这行
            }
        }catch (Exception e){
            e.printStackTrace();//连接错误、No Leader错误都可以通过重试解决；消息太大这类错误kafkaProducer不会进行任何重试，直接抛出异常
        }finally {
            producer.close();
        }

    }
*/



    /**
     *
     * 异步发送
     *
     * @param obj
     */
    public void sendMessageAsync(Object obj){
        String obj2String = JSONObject.toJSONString(obj);
        log.info("准备发送消息为：{}", obj2String);

        int messageNo = 1;

        for (;;){
            //发送消息
            ListenableFuture<SendResult<String, Object>> future = kafkaTemplate.send(TEST, obj);
            int finalMessageNo = messageNo;
            future.addCallback(new ListenableFutureCallback<SendResult<String, Object>>() {
                @Override
                public void onFailure(Throwable throwable) {
                    //发送失败的处理
                    log.info(TEST + " - 生产者 发送消息失败：" + throwable.getMessage());
                }

                @Override
                public void onSuccess(SendResult<String, Object> stringObjectSendResult) {
                    //成功的处理
                    log.info(TEST + " - 生产者 发送消息成功：" + stringObjectSendResult.toString());

                }

            });
            //生产1000条就退出
            if (messageNo%1000 == 0){
                log.info("成功发送了" + messageNo + "条");
                break;
            }
            messageNo++;

        }

    }

    /**
     * 异步发送
     *
     * @param obj
     */
    /*public void sendMessageAsync2(Object obj){
        ProducerRecord<String, String> record = new ProducerRecord<>("CustomCountry", "Precision Products", "France");//Topic Key Value
        producer.send(record, new DemoProducerCallback());//发送消息时，传递一个回调对象，该回调对象必须实现org.apahce.kafka.clients.producer.Callback接口

    }

    private class DemoProducerCallback implements Callback {
        @Override
        public void onCompletion(RecordMetadata recordMetadata, Exception e) {
            if (e != null) {//如果Kafka返回一个错误，onCompletion方法抛出一个non null异常。
                e.printStackTrace();//对异常进行一些处理，这里只是简单打印出来
            }
        }
    }*/





    public static void main(String[] args) throws InterruptedException {

        long start = System.currentTimeMillis();
        System.out.println(start+"\n");

        Thread.sleep(1L);

        System.out.println(System.currentTimeMillis() - start);
    }








}
