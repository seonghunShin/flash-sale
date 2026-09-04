package com.example.flashsale.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String brand;
    private Integer stock;
    private Integer price;
    private Integer status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Product : OrderItem = 1 : N
    @OneToMany(mappedBy = "product")
    private List<OrderItem> orderItems = new ArrayList<>();

    public void decreaseStock() {
        stock--;
    }

    public void changePrice(Integer price) {
        this.price = price;
    }
}