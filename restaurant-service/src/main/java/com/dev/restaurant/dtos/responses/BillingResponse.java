package com.dev.restaurant.dtos.responses;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Builder;

@Builder
public record BillingResponse(
  Long id,
  Long orderId,
  BigDecimal subtotal,
  Integer discount,
  Integer serviceTax,
  BigDecimal total,
  LocalDateTime createdAt,
  LocalDateTime closedAt
) {}
