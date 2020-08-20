package com.ccloud.oa.user.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author : breeze
 * @date : 2020/8/19
 * @description : 线程池
 */
@Configuration
public class ThreadPoolConfig {

    @Bean
    public ThreadPoolExecutor threadPoolExecutor(){
        return new ThreadPoolExecutor(
                12,
                120,
                60,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(200),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.CallerRunsPolicy());
    }
}
