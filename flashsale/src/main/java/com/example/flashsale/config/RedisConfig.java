//package com.example.flashsale.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.serializer.RedisSerializer;
//
//// 1목표: Redis template 등록
//@Configuration
//public class RedisConfig {
//    @Bean
//    public RedisTemplate<String, Object> redisTemplate() {
//        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
//
//        redisTemplate.setKeySerializer(RedisSerializer.string());
//        // Redis에게 json을 redis 형식에 맞게 번역해주는 것.
//        redisTemplate.setValueSerializer(RedisSerializer.json());
//
//        redisTemplate.setHashKeySerializer(RedisSerializer.string());
//        redisTemplate.setHashValueSerializer(RedisSerializer.json());
//
//        return redisTemplate;
//    }
//}
