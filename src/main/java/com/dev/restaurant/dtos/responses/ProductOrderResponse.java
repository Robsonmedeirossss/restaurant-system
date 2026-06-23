package com.dev.restaurant.dtos.responses;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.dev.restaurant.enums.StatusProductOrder;

import lombok.Builder;

@Builder
public record ProductOrderResponse(
  Long id,
  Long productId,
  Long orderId,
  BigDecimal unityPrice,
  Integer quantity,
  String observation,
  LocalDateTime preparationStartAt,
  LocalDateTime preparationEndAt,
  StatusProductOrder status,
  LocalDateTime createdAt
) {}
