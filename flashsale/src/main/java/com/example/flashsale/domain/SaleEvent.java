package com.example.flashsale.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "sale_events")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SaleEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productId;

    private Integer eventStock;

    private Integer status;

    private LocalDateTime startsAt;
}