package com.shuyx.shuyxfile.config;

import com.shuyx.shuyxfile.utils.MyMinioClient;
import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 86182
 * @version 1.0
 * @description: TODO
 * @date 2024/3/23 1:53
 */
@Configuration
public class MinioConfig {

    /**
     * 访问地址
     */
    @Value("${minio.url}")
    private String endpoint;

    /**
     * accessKey类似于用户ID，用于唯一标识你的账户
     */
    @Value("${minio.accessKey}")
    private String accessKey;

    /**
     * secretKey是你账户的密码
     */
    @Value("${minio.secretKey}")
    private String secretKey;

    @Bean
    public MyMinioClient minioClient() {
        MinioClient minioClient = MinioClient.builder()
                .endpoint(endpoint)
                .credentials(accessKey, secretKey)
                .build();
        return new MyMinioClient(minioClient);
    }

}
