package com.shuyx.shuyxuser.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisTemplateConfig {
    /**
     * 自定义redis的序列化方式
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory){
        // 创建RedisTemplate对象
        RedisTemplate template = new RedisTemplate<String, Object>();
        // 设置连接工厂
        template.setConnectionFactory(connectionFactory);
        // jackson2的序列化方式
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        // 配置key的序列化方式
        template.setKeySerializer(new StringRedisSerializer());
        // 配置hash数据的key的序列化方式
        template.setHashKeySerializer(new StringRedisSerializer());
        // 配置value的序列化方式
        template.setValueSerializer(jackson2JsonRedisSerializer);
        // 配置hash数据的value的序列化方式
        template.setHashValueSerializer(jackson2JsonRedisSerializer);
        return template;
    }


}
