package com.dev.restaurant.dtos.responses.indicators;

import lombok.Builder;

@Builder 
public record ProductAverageTimeResponse(
    String name,
    Double averagePreparationsTime
) {}
