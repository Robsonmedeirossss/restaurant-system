package com.dev.restaurant.dtos.requests.creates;

import java.math.BigDecimal;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record BillingRequest(
  @NotNull(message = "Campo orderId é obrigatório")
  @PositiveOrZero(message = "Campo price desse ser maior ou igual a zero")
  Long orderId,

  @NotNull(message = "Campo subtotal é obrigatório")
  BigDecimal subtotal,

  @Min(value = 0, message = "Discount deve ser no mínimo 0 e no máximo 100")
  @Max(value = 100, message = "Discount deve ser no mínimo 0 e no máximo 100")
  Integer discount,

  @Min(value = 0, message = "Discount deve ser no mínimo 0 e no máximo 100")
  @Max(value = 100, message = "Discount deve ser no mínimo 0 e no máximo 100")
  Integer serviceTax
) {}
