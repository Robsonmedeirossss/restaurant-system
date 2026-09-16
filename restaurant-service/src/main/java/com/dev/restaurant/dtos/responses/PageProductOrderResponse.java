package com.dev.restaurant.dtos.responses;

import java.util.List;

import lombok.Builder;

@Builder
public record PageProductOrderResponse(
    List<ProductOrderResponse> productsOrder,
    int page,
    int totalPages,
    int totalElements
) {}
