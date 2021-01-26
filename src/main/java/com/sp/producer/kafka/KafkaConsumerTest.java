package com.sp.producer.kafka;


import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.ThreadLocalRandom;



public class KafkaConsumerTest{



    private final Consumer<String, String> consumer;
    private final int expectedCount; // 用于测试的消息数量

    public KafkaConsumerTest(String brokerId, String topic, String groupID, int expectedCount) {
        Properties props = new Properties();
        props.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, brokerId);
        props.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.setProperty(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        props.setProperty(ConsumerConfig.GROUP_ID_CONFIG, groupID);
        props.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Collections.singletonList(topic));
        this.expectedCount = expectedCount;
    }

    public void run() {
        try {
            int alreadyConsumed = 0;
            while (alreadyConsumed < expectedCount) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(1));
                alreadyConsumed += records.count();
                records.forEach(this::handleRecord);
            }
        } finally {
            consumer.close();
        }
    }

    private void handleRecord(ConsumerRecord<String, String> record) {
        try {
            // 模拟每条消息10毫秒处理
            Thread.sleep(ThreadLocalRandom.current().nextInt(10));
        } catch (InterruptedException ignored) {
            Thread.currentThread().interrupt();
        }
        System.out.println(Thread.currentThread().getName() + " finished message processed. Record offset = " + record.offset());
    }




    public static void main(String[] args) throws InterruptedException {

        int expectedCount = 50 * 900;
        String brokerId = "192.170.15.29:9092";
        String groupId = "test-group";
        String topic = "test";

//        KafkaConsumerTest consumer = new KafkaConsumerTest(brokerId, topic, groupId + "-single", expectedCount);
        long start = System.currentTimeMillis();
//        consumer.run();
//        System.out.println("Single-threaded consumer costs " + (System.currentTimeMillis() - start));

//        Thread.sleep(1L);

        /*MultiThreadedConsumer multiThreadedConsumer =
                new MultiThreadedConsumer(brokerId, topic, groupId + "-multi", expectedCount);
        start = System.currentTimeMillis();
        multiThreadedConsumer.run();
        System.out.println("Multi-threaded consumer costs " + (System.currentTimeMillis() - start));*/
    }






}
