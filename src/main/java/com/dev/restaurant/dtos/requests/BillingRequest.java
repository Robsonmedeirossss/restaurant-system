package com.dev.restaurant.dtos.requests;

import java.math.BigDecimal;

public record BillingRequest(
  Long orderId,
  BigDecimal subtotal,
  Integer discount,
  Integer serviceTax,
  BigDecimal total
) {}
