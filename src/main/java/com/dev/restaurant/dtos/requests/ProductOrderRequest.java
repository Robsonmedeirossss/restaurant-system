package com.dev.restaurant.dtos.requests;

import java.math.BigDecimal;

import com.dev.restaurant.enums.StatusProductOrder;

public record ProductOrderRequest(
  Long productId,
  Long orderId,
  BigDecimal unityPrice,
  Integer quantity,
  String observation,
  StatusProductOrder status
) {}