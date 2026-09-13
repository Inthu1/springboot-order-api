package com.example.orderservice.service;

import com.example.orderservice.client.ProductClient;
import com.example.orderservice.dto.OrderRequest;
import com.example.orderservice.dto.OrderResponse;
import com.example.orderservice.dto.ProductResponse;
import com.example.orderservice.entity.Order;
import com.example.orderservice.repository.OrderRepository;
import java.math.BigDecimal;

import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductClient productClient;

    public OrderServiceImpl(
            OrderRepository orderRepository,
            ProductClient productClient) {

        this.orderRepository = orderRepository;
        this.productClient = productClient;
    }

    @Override
    public OrderResponse createOrder(OrderRequest request) {

        // Communicate with Product Service
        ProductResponse product =
                productClient.getProduct(
                        request.getProductId()
                );

        // Calculate total amount
        BigDecimal totalAmount =
                product.getPrice()
                        .multiply(
                                BigDecimal.valueOf(
                                        request.getQuantity()
                                )
                        );

        // Create Order entity
        Order order = new Order();

        order.setProductId(request.getProductId());
        order.setQuantity(request.getQuantity());
        order.setTotalAmount(totalAmount);

        Order savedOrder =
                orderRepository.save(order);

        // Entity → Response DTO
        OrderResponse response = new OrderResponse();

        response.setId(savedOrder.getId());
        response.setProductId(savedOrder.getProductId());
        response.setQuantity(savedOrder.getQuantity());
        response.setTotalAmount(
                savedOrder.getTotalAmount()
        );

        return response;
    }
}