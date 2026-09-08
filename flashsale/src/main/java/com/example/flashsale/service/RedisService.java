//package com.example.flashsale.service;
//
//import com.example.flashsale.dto.OrderDto;
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.stereotype.Service;
//
//import java.time.Duration;
//import java.time.LocalDateTime;
//
//@Service
//@RequiredArgsConstructor
//public class RedisService {
//    // config에서 실제로 객체로 만들어 Bean으로 등록을 했기 때문에 new를 안해도 생성이 됨.
//    private final RedisTemplate<String, Object> redisTemplate;
//
//    public void sample() {
//
////        redisTemplate.opsForList().set();
////        redisTemplate.opsForValue().get();
//        // String저장
//        // List 저장
//        // Set 저장
//        // 등등 다양한 데이터를 저장할 수 있다.
//
////        redisTemplate.opsForValue().set("name", "kim");
////        UserDto o = (UserDto) redisTemplate.opsForValue().get("name");
//
//        OrderDto orderDto = new OrderDto(1L, 1200, 1, LocalDateTime.now());
//        redisTemplate.opsForValue().set("order:1", orderDto, Duration.ofMinutes(5));
//
//        redisTemplate.delete("키를 직접 입력");
//        Boolean b = redisTemplate.hasKey("타겟");
//        // 중복요청 방지
//        Boolean b1 = redisTemplate.opsForValue().setIfAbsent("lock:payment:100", "Locked", Duration.ofSeconds(10));
//
//        redisTemplate.opsForList().rightPush("queue", "user1");
//        redisTemplate.opsForList().rightPush("queue", "user2");
//        redisTemplate.opsForList().rightPush("queue", "user3");
//
//        // queue
//        // user1 user2 user3 ㅁㅁㅁㅁ
//        Object o = redisTemplate.opsForList().leftPop("queue"); // --> user1
//        // user2 user3 ㅁㅁㅁㅁ
//
//        // user1의 최근 검색어를 저장하고 사용자가 편하게 조회하도록 개발
//
//        // 3번 호출
//        redisTemplate.opsForZSet().add("user:1:search", "스프링 개발", 1);
//        // 1번 호출
//        redisTemplate.opsForZSet().add("user:1:search", "버스 시간", 1);
//
//        // "user:1:search" 최근 검색어 "스프링 개발", 3; "버스 시간", 1;
//
//
//        // 인플럭스 DB, 엘라스틱서치 (시계열DB?)
//        // 기술 적용했을 때 왜 이 기술을 선택했고 이 기술에 어떠한 문제점이 있었고 문제점을 어떻게 해결해나갔는가.
//
//    }
//}
