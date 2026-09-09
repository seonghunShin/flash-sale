package com.example.flashsale.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StockHoldService {

    private final StringRedisTemplate redisTemplate;

    private static final String HOLD_SCRIPT = """
            if redis.call('EXISTS', KEYS[2]) == 1 then
                return 0
            end

            local stock =
                tonumber(
                    redis.call('GET', KEYS[1])
                )

            if not stock or stock <= 0 then
                return 0
            end

            redis.call('DECR', KEYS[1])
            redis.call('SET', KEYS[2], '1')

            return 1
            """;

    private static final String RELEASE_SCRIPT = """
            if redis.call('DEL', KEYS[2]) == 1 then
                redis.call('INCR', KEYS[1])
                return 1
            end

            return 0
            """;

    private String stockKey(
            Long eventId
    ) {
        return "flash:" +
                eventId +
                ":stock";
    }

    private String holdKey(
            Long eventId,
            Long userId
    ) {
        return "flash:" +
                eventId +
                ":hold:" +
                userId;
    }

    public void initStock(
            Long eventId,
            int stock
    ) {

        redisTemplate
                .opsForValue()
                .set(
                        stockKey(eventId),
                        String.valueOf(stock)
                );
    }

    public boolean hold(
            Long eventId,
            Long userId
    ) {

        DefaultRedisScript<Long> script =
                new DefaultRedisScript<>(
                        HOLD_SCRIPT,
                        Long.class
                );

        Long result =
                redisTemplate.execute(
                        script,
                        List.of(
                                stockKey(eventId),
                                holdKey(
                                        eventId,
                                        userId
                                )
                        )
                );

        return result != null &&
                result == 1;
    }

    public boolean hasHold(
            Long eventId,
            Long userId
    ) {

        return Boolean.TRUE.equals(
                redisTemplate.hasKey(
                        holdKey(
                                eventId,
                                userId
                        )
                )
        );
    }

    public void release(
            Long eventId,
            Long userId
    ) {

        DefaultRedisScript<Long> script =
                new DefaultRedisScript<>(
                        RELEASE_SCRIPT,
                        Long.class
                );

        redisTemplate.execute(
                script,
                List.of(
                        stockKey(eventId),
                        holdKey(
                                eventId,
                                userId
                        )
                )
        );
    }
}