package com.example.flashsale.repository;

import com.example.flashsale.domain.SaleEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleEventRepository
        extends JpaRepository<SaleEvent, Long> {
}