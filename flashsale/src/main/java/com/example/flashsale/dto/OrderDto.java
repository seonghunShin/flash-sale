package com.example.flashsale.dto;

import java.time.LocalDateTime;

public record OrderDto(
        Long id,
        Integer totalPrice,
        Integer status,
        LocalDateTime createdAt
) {
}