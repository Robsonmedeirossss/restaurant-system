package com.dev.restaurant.dtos.requests.creates;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record BillingRequest(
  @NotNull(message = "Campo orderId é obrigatório")
  @Positive(message = "Campo orderId desse ser maior ou igual a zero")
  Long orderId,

  @Min(value = 0, message = "Discount deve ser no mínimo 0 e no máximo 100")
  @Max(value = 100, message = "Discount deve ser no mínimo 0 e no máximo 100")
  Integer discount,

  @Min(value = 0, message = "Discount deve ser no mínimo 0 e no máximo 100")
  @Max(value = 100, message = "Discount deve ser no mínimo 0 e no máximo 100")
  Integer serviceTax,

  LocalDateTime closedAt
) {}
