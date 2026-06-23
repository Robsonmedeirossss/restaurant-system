package com.dev.restaurant.dtos.responses;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.dev.restaurant.enums.PaymentType;
import com.dev.restaurant.enums.StatusPayment;

import lombok.Builder;

@Builder
public record PaymentResponse(
  Long id,
  Long orderId,
  BigDecimal value,
  PaymentType paymentType,
  StatusPayment status,
  String externalTransactionCode,
  LocalDateTime paymentDate,
  LocalDateTime createdAt
) {}
