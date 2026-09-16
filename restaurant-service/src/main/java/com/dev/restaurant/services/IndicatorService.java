package com.dev.restaurant.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.dev.restaurant.dtos.responses.indicators.IndicatorResponse;
import com.dev.restaurant.dtos.responses.indicators.OrderByStatusResponse;
import com.dev.restaurant.dtos.responses.indicators.OrderIndicatorResponse;
import com.dev.restaurant.dtos.responses.indicators.ProductAverageTimeResponse;
import com.dev.restaurant.dtos.responses.indicators.ProductIndicatorResponse;
import com.dev.restaurant.dtos.responses.indicators.TopProductsResponse;
import com.dev.restaurant.projections.OrderIndicatorsProjection;
import com.dev.restaurant.projections.OrdersByStatusProjection;
import com.dev.restaurant.projections.PreparationIndicatorsProjection;
import com.dev.restaurant.projections.TopProductsProjection;
import com.dev.restaurant.repositories.OrderRepository;
import com.dev.restaurant.repositories.ProductOrderRepository;
import com.dev.restaurant.repositories.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IndicatorService {
    
    private final OrderRepository orderRepository;
    private final ProductOrderRepository productOrderRepository;
    private final ProductRepository productRepository;

    public IndicatorResponse getOrderIndicators(LocalDate from, LocalDate to) {
        OrderIndicatorsProjection orderIndicatorsProjection = this.orderRepository.orderIndicadotors(from, to);
        List<OrdersByStatusProjection> ordersByStatusProjection = this.orderRepository.getTotalOrdersByStatus(from, to);
        Long totalProductsSold = this.productOrderRepository.getTotalProductsSold(from, to);
        List<PreparationIndicatorsProjection> averagePreparationMinutes = this.productRepository.getAveragPreparationMinutes();
        List<TopProductsProjection> topProductsProjection = this.productRepository.getTopProducts(from, to);

        OrderIndicatorResponse ordersIndicators = OrderIndicatorResponse.builder()
        .totalOrders(orderIndicatorsProjection.getTotalOrders())
        .averageOrderValue(orderIndicatorsProjection.getAverageOrderValue())
        .totalRevenue(orderIndicatorsProjection.getTotalRevenue())
        .byStatus(ordersByStatusProjection.stream()
            .map((order) -> new OrderByStatusResponse(order.getStatus(), order.getQuantity()))
            .toList()
        )
        .build();

        ProductIndicatorResponse productsIndicators = ProductIndicatorResponse.builder()
        .totalSold(totalProductsSold)
        .averagePreparationTime(averagePreparationMinutes.stream()
            .map((product) -> new ProductAverageTimeResponse(product.getProductName(), product.getAveragePreparationTime()))
            .toList()
        )
        .topProducts(topProductsProjection.stream()
            .map((product) -> new TopProductsResponse(product.getProductName(),product.getQuantitySold(), product.getRevenue()))
            .toList()
        )
        .build();

        return IndicatorResponse.builder()
            .ordersIndicators(ordersIndicators)
            .productsIndicators(productsIndicators)
        .build();
    }
}
