package com.dev.restaurant.dtos.responses.indicators;

import java.math.BigDecimal;

import lombok.Builder;

@Builder 
public record TopProductsResponse(
    String name,
    Long totalSold,
    BigDecimal totalRevenue
) {}
