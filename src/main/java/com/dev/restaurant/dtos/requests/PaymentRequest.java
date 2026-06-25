package com.dev.restaurant.dtos.requests;

import java.time.LocalDateTime;

import com.dev.restaurant.enums.PaymentType;
import com.dev.restaurant.enums.StatusPayment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PaymentRequest(
  @NotNull(message = "Campo orderId não pode ser vazio")
  Long orderId,
  @NotBlank(message = "Campo paymentTyp não pode ser vazio")
  PaymentType paymentType,
  StatusPayment status,
  String externalTransactionCode,
  LocalDateTime paymentDate,
  LocalDateTime createdAt
) {}
