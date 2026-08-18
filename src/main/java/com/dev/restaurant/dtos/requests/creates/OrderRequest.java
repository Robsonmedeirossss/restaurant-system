package com.dev.restaurant.dtos.requests.creates;

import java.time.LocalDateTime;

import com.dev.restaurant.enums.StatusOrder;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record OrderRequest(
  StatusOrder status,

  @NotNull(message = "Campo tableId não pode ser nulo") 
  @Positive(message = "Campo tableId deve ser maior ou igual a zero")
  Long tableId,

  LocalDateTime openingDate,
  
  String observation
) {} 
