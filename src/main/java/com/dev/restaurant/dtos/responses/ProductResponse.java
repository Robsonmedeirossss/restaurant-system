package com.dev.restaurant.dtos.responses;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Builder;

@Builder
public record ProductResponse(
  Long id,
  String name,
  String description,
  BigDecimal price,
  Boolean available,
  Integer preparationMinutes,
  Integer stock,
  Long categoryId,
  LocalDateTime createdAt
) {}
