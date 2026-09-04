package com.example.flashsale.controller;

import com.example.flashsale.dto.OrderDto;
import com.example.flashsale.dto.ProductDto;
import com.example.flashsale.service.QueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class QueryController {

    private final QueryService queryService;

    @GetMapping("/products/search")
    public ProductDto findByName(
            @RequestParam String name
    ) {

        return queryService.findByName(name);
    }

    @GetMapping("/products")
    public List<ProductDto> products(
            @RequestParam String brand
    ) {

        return queryService.findBrandProducts(
                brand
        );
    }

    @GetMapping("/users/{userId}/orders")
    public List<OrderDto> orders(
            @PathVariable Long userId
    ) {

        return queryService.findUserOrders(
                userId
        );
    }

    @GetMapping("/products/{productId}")
    public ProductDto product(
            @PathVariable Long productId
    ) {

        return queryService.getProduct(
                productId
        );
    }
}