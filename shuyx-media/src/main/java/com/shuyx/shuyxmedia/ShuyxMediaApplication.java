package com.shuyx.shuyxmedia;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients   //openfeign启动注解
@MapperScan("com.shuyx.shuyxmedia.mapper")
public class ShuyxMediaApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShuyxMediaApplication.class, args);
    }

}
