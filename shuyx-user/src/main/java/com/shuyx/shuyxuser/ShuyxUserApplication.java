package com.shuyx.shuyxuser;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.shuyx.shuyxuser.mapper")
public class ShuyxUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShuyxUserApplication.class, args);
    }

}
