package com.example.flashsale.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WaitingQueueService {

    private final StringRedisTemplate redisTemplate;

    private String queueKey(
            Long eventId
    ) {
        return "flash:" +
                eventId +
                ":queue";
    }

    private String seqKey(
            Long eventId
    ) {
        return "flash:" +
                eventId +
                ":seq";
    }

    private String admittedKey(
            Long eventId
    ) {
        return "flash:" +
                eventId +
                ":admitted";
    }

    public void enter(
            Long eventId,
            Long userId
    ) {

        Long sequence =
                redisTemplate
                        .opsForValue()
                        .increment(
                                seqKey(eventId)
                        );

        redisTemplate
                .opsForZSet()
                .add(
                        queueKey(eventId),
                        String.valueOf(userId),
                        sequence
                );
    }

    public Long rank(
            Long eventId,
            Long userId
    ) {

        Long rank =
                redisTemplate
                        .opsForZSet()
                        .rank(
                                queueKey(eventId),
                                String.valueOf(userId)
                        );

        return rank == null
                ? null
                : rank + 1;
    }

    public Set<String> admit(
            Long eventId,
            long count
    ) {

        Set<String> users =
                redisTemplate
                        .opsForZSet()
                        .popMin(
                                queueKey(eventId),
                                count
                        )
                        .stream()
                        .map(
                                ZSetOperations.TypedTuple::getValue
                        )
                        .collect(
                                Collectors.toSet()
                        );

        if (!users.isEmpty()) {

            redisTemplate
                    .opsForSet()
                    .add(
                            admittedKey(eventId),
                            users.toArray(String[]::new)
                    );
        }

        return users;
    }

    public boolean isAdmitted(
            Long eventId,
            Long userId
    ) {

        return Boolean.TRUE.equals(
                redisTemplate
                        .opsForSet()
                        .isMember(
                                admittedKey(eventId),
                                String.valueOf(userId)
                        )
        );
    }
}