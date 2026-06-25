package com.dev.restaurant.dtos.requests;

import com.dev.restaurant.enums.StatusProductOrder;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record ProductOrderRequest(
  @NotNull(message = "Campo productId é obrigatório")
  @PositiveOrZero(message = "Campo productId deve ser maior ou igual a zero")
  Long productId,

  @NotNull(message = "Campo orderId é obrigatório")
  @PositiveOrZero(message = "Campo orderId deve ser maior ou igual a zero")
  Long orderId,

  @NotNull(message = "Campo orderId é obrigatório")
  @PositiveOrZero(message = "Campo orderId deve ser maior ou igual a zero") 
  Integer quantity,
  String observation,
  StatusProductOrder status
) {}