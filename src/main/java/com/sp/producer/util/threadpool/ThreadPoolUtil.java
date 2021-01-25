package com.sp.producer.util.threadpool;


import java.util.concurrent.*;

/**
 *
 * 线程池工具类
 *
 * @author yushu.zhao
 * @create 2020-12-24 16:24
 */
public class ThreadPoolUtil {



    /**
     * 新建一个线程池
     *
     * @param threadSize 同时执行的线程数大小
     * @return ExecutorService
     */
    public static ExecutorService newExecutor(int threadSize){
        return new ThreadPoolExecutor(threadSize,threadSize,0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingDeque<Runnable>());

    }


    /**
     *
     * 新建一个NewFixedThreadPool线程池
     *
     * @param threadSize 同时执行的线程数
     * @return ExecutorService
     *//*
    public static ExecutorService NewFixedThreadPool(int threadSize){




    }*/













}
