package com.example.flashsale.dto;

public record ProductDto(
        Long id,
        String name,
        String brand,
        Integer price,
        Integer stock
) {
}