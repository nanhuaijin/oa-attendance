package com.ccloud.oa.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author : breeze
 * @date : 2020/8/14
 * @description :
 */
@SpringBootApplication
@MapperScan(basePackages = "com.ccloud.oa.user.mapper")
public class UserApp {
    public static void main(String[] args) {
        SpringApplication.run(UserApp.class, args);
    }
}
