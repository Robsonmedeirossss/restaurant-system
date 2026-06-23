package com.dev.restaurant.dtos.requests;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.dev.restaurant.enums.PaymentType;
import com.dev.restaurant.enums.StatusPayment;

public record PaymentRequest(
  Long orderId,
  BigDecimal value,
  PaymentType paymentType,
  StatusPayment status,
  String externalTransactionCode,
  LocalDateTime paymentDate,
  LocalDateTime createdAt
) {}
