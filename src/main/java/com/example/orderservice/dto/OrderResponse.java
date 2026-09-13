package com.example.orderservice.dto;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderResponse {

    private Long id;

    private Long productId;

    private Integer quantity;

    private BigDecimal totalAmount;

    // getters and setters
}