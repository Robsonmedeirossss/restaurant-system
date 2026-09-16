package com.dev.restaurant.dtos.responses.indicators;

import lombok.Builder;

@Builder 
public record IndicatorResponse(
    OrderIndicatorResponse ordersIndicators,
    ProductIndicatorResponse productsIndicators
) {}
