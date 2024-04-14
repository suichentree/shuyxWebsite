package com.shuyx.shuyxuser;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableFeignClients   //openfeign启动注解
@MapperScan("com.shuyx.shuyxuser.mapper")
//该注解的作用是扫描'com.shuyx路径下的所有bean',也可以把其他模块的bean扫描到。
//无注解的情况下是无法把其他模块的bean注入到自身模块的bean容器中的。但是添加这个注解可以。
@ComponentScan("com.shuyx")
public class ShuyxUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShuyxUserApplication.class, args);
    }

}
