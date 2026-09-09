package com.dev.restaurant.dtos.requests.creates;

import com.dev.restaurant.enums.PaymentType;

import jakarta.validation.constraints.NotNull;

public record PaymentRequest(
  @NotNull(message = "Campo orderId não pode ser vazio")
  Long orderId,
  @NotNull(message = "Campo paymentTyp não pode ser vazio")
  PaymentType paymentType
) {}
