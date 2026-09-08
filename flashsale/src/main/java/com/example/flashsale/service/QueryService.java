package com.example.flashsale.service;

import com.example.flashsale.domain.Product;
import com.example.flashsale.dto.OrderDto;
import com.example.flashsale.dto.ProductDto;
import com.example.flashsale.repository.OrderRepository;
import com.example.flashsale.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QueryService {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final TransactionTemplate transactionTemplate;



    public ProductDto findByName(
            String name
    ) {

        Product product =
                productRepository
                        .findByName(name)
                        .orElseThrow();

        return toProductDto(product);
    }

    public List<ProductDto> findBrandProducts(
            String brand
    ) {

        return productRepository
                .findTop20ByBrandAndStatusOrderByPriceAsc(
                        brand,
                        1
                )
                .stream()
                .map(this::toProductDto)
                .toList();
    }

    public List<OrderDto> findUserOrders(
            Long userId
    ) {

        return orderRepository
                .findTop10ByUsersIdOrderByCreatedAtDesc(
                        userId
                )
                .stream()
                .map(order ->
                        new OrderDto(
                                order.getId(),
                                order.getTotalPrice(),
                                order.getStatus(),
                                order.getCreatedAt()
                        )
                )
                .toList();
    }

    @Cacheable(
            cacheNames = "product:detail",
            key = "#productId"
    )
    public ProductDto getProduct(
            Long productId
    ) {

        Product product =
                productRepository
                        .findById(productId)
                        .orElseThrow();

        return toProductDto(product);
    }

    private ProductDto toProductDto(
            Product product
    ) {

        return new ProductDto(
                product.getId(),
                product.getName(),
                product.getBrand(),
                product.getPrice(),
                product.getStock()
        );
    }

    @Transactional
    @CacheEvict(
            cacheNames = "product:detail",
            key = "#productId"
    )
    public void changePrice(
            Long productId,
            Integer price
    ) {

        Product product =
                productRepository
                        .findById(productId)
                        .orElseThrow();

        product.changePrice(price);
    }
}