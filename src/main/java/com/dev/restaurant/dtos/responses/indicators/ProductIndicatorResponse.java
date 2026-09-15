package com.dev.restaurant.dtos.responses.indicators;

import java.util.List;

import lombok.Builder;

@Builder 
public record ProductIndicatorResponse(
    Long totalSold,
    List<TopProductsResponse> topProducts,
    List<ProductAverageTimeResponse> averagePreparationTime
) {}
