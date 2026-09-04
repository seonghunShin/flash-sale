package com.example.flashsale.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Payment {

    @Id
    @Column(length = 80)
    private String paymentId;

    private Long eventId;

    private Long productId;

    private Long userId;

    private Integer amount;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    private LocalDateTime createdAt;

    private LocalDateTime paidAt;

    public Payment(
            String paymentId,
            Long eventId,
            Long productId,
            Long userId,
            Integer amount
    ) {
        this.paymentId = paymentId;
        this.eventId = eventId;
        this.productId = productId;
        this.userId = userId;
        this.amount = amount;
        this.status = PaymentStatus.PENDING;
        this.createdAt = LocalDateTime.now();
    }

    public void pay() {

        if (status == PaymentStatus.PENDING) {
            status = PaymentStatus.PAID;
            paidAt = LocalDateTime.now();
        }
    }

    public void fail() {

        if (status == PaymentStatus.PENDING) {
            status = PaymentStatus.FAILED;
        }
    }

    public void verifyFail() {

        if (status == PaymentStatus.PENDING) {
            status = PaymentStatus.VERIFY_FAILED;
        }
    }
}