package com.dev.restaurant.dtos.requests.creates;

import com.dev.restaurant.enums.PaymentType;

import jakarta.validation.constraints.NotNull;

public record PaymentRequest(
  @NotNull(message = "Campo paymentTyp não pode ser vazio")
  PaymentType paymentType
) {}
