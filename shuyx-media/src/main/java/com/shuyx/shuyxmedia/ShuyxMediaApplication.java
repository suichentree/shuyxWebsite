package com.shuyx.shuyxmedia;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.shuyx.shuyxmedia.mapper")
public class ShuyxMediaApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShuyxMediaApplication.class, args);
    }

}
