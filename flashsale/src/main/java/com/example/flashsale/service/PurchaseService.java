package com.example.flashsale.service;

import com.example.flashsale.domain.Product;
import com.example.flashsale.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PurchaseService {

    private final ProductRepository productRepository;

    @Transactional
    public boolean buy(
            Long productId
    ) {

        Product product =
                productRepository
                        .findById(productId)
                        .orElseThrow();

        if (product.getStock() <= 0) {
            return false;
        }

        product.decreaseStock();

        return true;
    }
}