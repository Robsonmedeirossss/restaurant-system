package com.dev.restaurant.dtos.responses.indicators;

import com.dev.restaurant.enums.StatusOrder;

import lombok.Builder;

@Builder  
public record OrderByStatusResponse(
    StatusOrder status,
    Long totalSold
) {}
