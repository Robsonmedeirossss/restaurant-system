package com.dev.restaurant.dtos.responses;

import java.time.LocalDateTime;

import com.dev.restaurant.enums.StatusOrder;

import lombok.Builder;

@Builder
public record OrderResponse(
  Long id,
  StatusOrder status,
  Long tableId,
  LocalDateTime openingDate,
  LocalDateTime closingDate,
  String observation,
  LocalDateTime createdAt
) {}
