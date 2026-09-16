package com.dev.restaurant.dtos.responses.indicators;

import java.math.BigDecimal;
import java.util.List;

import lombok.Builder;

@Builder 
public record OrderIndicatorResponse(
    Long totalOrders,
    BigDecimal totalRevenue,
    BigDecimal averageOrderValue,
    List<OrderByStatusResponse> byStatus
) {}
