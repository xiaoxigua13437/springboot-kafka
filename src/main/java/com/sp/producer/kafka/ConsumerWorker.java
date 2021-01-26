package com.sp.producer.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantLock;


/**
 * 功能描述:消息处理的Runnable类<p>
 *     <ul>注:
 *         <li>1.将某个分区消息分配Worker(一旦消息分配，可以暂停该分区的)</li>
 *         <li></li>
 *
 *     </ul>
 *
 *
 *
 *
 * @author yushu.zhao
 * @create 2020-12-28 17:13
 *
 */
public class ConsumerWorker<K, V> {

    private final List<ConsumerRecord<K, V>> recordsOfSamePartition;
    private volatile boolean started = false;
    private volatile boolean stopped = false;
    private final ReentrantLock lock = new ReentrantLock();

    private final long INVALID_COMMITTED_OFFSET = -1L;
    private final AtomicLong latestProcessedOffset = new AtomicLong(INVALID_COMMITTED_OFFSET);
    private final CompletableFuture<Long> future = new CompletableFuture<>();

    public ConsumerWorker(List<ConsumerRecord<K, V>> recordsOfSamePartition) {
        this.recordsOfSamePartition = recordsOfSamePartition;
    }

    public boolean run() {
        lock.lock();
        if (stopped)
            return false;
        started = true;
        lock.unlock();
        for (ConsumerRecord<K, V> record : recordsOfSamePartition) {
            if (stopped)
                break;
            handleRecord(record);
            if (latestProcessedOffset.get() < record.offset() + 1)
                latestProcessedOffset.set(record.offset() + 1);
        }
        return future.complete(latestProcessedOffset.get());
    }

    public long getLatestProcessedOffset() {
        return latestProcessedOffset.get();
    }

    private void handleRecord(ConsumerRecord<K, V> record) {
        try {
            //模拟每条消息10ms处理时间
            Thread.sleep(ThreadLocalRandom.current().nextInt(10));
        } catch (InterruptedException ignored) {
            Thread.currentThread().interrupt();
        }
        System.out.printf("topic is %s, offset is %d, value is %s \n", record.topic(), record.offset(), record.value());
//        System.out.println(Thread.currentThread().getName() + " finished message processed. Record offset = " + record.offset());
    }

    public void close() {
        lock.lock();
        this.stopped = true;
        if (!started) {
            future.complete(latestProcessedOffset.get());
        }
        lock.unlock();
    }

    public boolean isFinished() {
        return future.isDone();
    }

    public long waitForCompletion(long timeout, TimeUnit timeUnit) {
        try {
            return future.get(timeout, timeUnit);
        } catch (Exception e) {
            if (e instanceof InterruptedException)
                Thread.currentThread().interrupt();
            return INVALID_COMMITTED_OFFSET;
        }
    }
}