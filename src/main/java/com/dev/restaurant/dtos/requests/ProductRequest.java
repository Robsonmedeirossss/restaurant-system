package com.dev.restaurant.dtos.requests;

import java.math.BigDecimal;

import lombok.Builder;

@Builder
public record ProductRequest(
  String name,
  String description,
  BigDecimal price,
  Boolean available,
  Integer preparationMinutes,
  Integer Stock,
  Long categoryId
) {}
