package com.dev.restaurant.dtos.requests;

import java.time.LocalDateTime;

import com.dev.restaurant.enums.StatusOrder;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record OrderRequest(
  StatusOrder status,
  @NotNull(message = "Campo tableId não pode ser nulo")
  @PositiveOrZero(message = "Campo tableId deve ser maior ou igual a zero")
  Long tableId,
  LocalDateTime openingDate,
  String observation
) {} 
