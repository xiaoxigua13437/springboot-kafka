package com.sp.producer.kafka.thread;


import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 自定义单线程消费
 *
 * @author yushu.zhao
 * @create 2020-12-25 14:48
 */
public class TestConsumerThread implements Runnable{


    //定义消费者
    private final Consumer<String,String> consumer;

    //用于存储指定分区的消费位移数据
    private Map<TopicPartition, OffsetAndMetadata> offsets = new HashMap<>();


    //消费者初始化配置
    public TestConsumerThread(String brokerId, String topic, String groupID) {
        Properties props = new Properties();
        props.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, brokerId);
        props.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.setProperty(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        props.setProperty(ConsumerConfig.GROUP_ID_CONFIG, groupID);
        props.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Collections.singletonList(topic));//订阅主题
    }

    @Override
    public void run() {


        int count = 0;

        try {

            while (true){

                ConsumerRecords<String,String> consumerRecords = consumer.poll(Duration.ofSeconds(1));

                for (ConsumerRecord<String,String> record : consumerRecords ){

                    System.out.printf("topic is %s, offset is %d, value is %s \n", record.topic(), record.offset(), record.value());
                    offsets.put(new TopicPartition(record.topic(), record.partition()),
                            new OffsetAndMetadata(record.offset() + 1)); //待提交的位移为下一条消息的位移

                    count++;
                }

                System.out.println("count value:"+count);
            }

        }finally {

            try {

                consumer.commitSync(); //最后异常提交使用同步阻塞式提交

            }finally {
                consumer.close();

            }
        }


    }


    /*@Override
    public void run() {

        try {
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(1));
                records.forEach(this::handleRecord);//处理消息
                consumer.commitSync();//使用异步提交规避阻塞
            }

        }catch (Exception e){
            e.printStackTrace();

        } finally {

            try {
                consumer.commitSync(); //最后异常提交使用同步阻塞式提交
            }finally {
                consumer.close();
            }
        }

    }
*/

    /**
     * 模拟每条消息处理
     *
     *
     * @param record
     */
    private void handleRecord(ConsumerRecord<String, String> record) {
        try {
            // 模拟每条消息10毫秒处理
            Thread.sleep(ThreadLocalRandom.current().nextInt(10));
        } catch (InterruptedException ignored) {
            Thread.currentThread().interrupt();
        }
        System.out.println(Thread.currentThread().getName() + " finished message processed. Record offset = " + record.offset());
    }



    public static void main(String[] args){

        String brokerId = "192.170.15.29:9092";
        String groupId = "test-submit";
        String topic = "test";

        TestConsumerThread thread = new TestConsumerThread(brokerId, topic, groupId);
        long start = System.currentTimeMillis();
        Thread thread1 = new Thread(thread);
        thread1.start();
        System.out.println("Single-threaded consumer costs " + (System.currentTimeMillis() - start));


    }







}
