package com.ccloud.oa.sms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author : breeze
 * @date : 2020/9/1
 * @description : 短信服务启动类
 */
@SpringBootApplication
public class SmsApp {
    public static void main(String[] args) {
        SpringApplication.run(SmsApp.class, args);
    }
}
