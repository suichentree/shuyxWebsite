package com.shuyx.shuyxfile;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.shuyx.shuyxfile.mapper")
public class ShuyxFileApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShuyxFileApplication.class, args);
    }

}
