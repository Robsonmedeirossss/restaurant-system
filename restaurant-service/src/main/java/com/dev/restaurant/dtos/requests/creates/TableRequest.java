package com.dev.restaurant.dtos.requests.creates;

import com.dev.restaurant.enums.StatusTable;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record TableRequest(
  @NotNull(message = "Campo number não pode ser nulo")
  @PositiveOrZero(message = "Campo number desse ser maior ou igual a zero")  
  Integer number,
  @Positive(message = "Campo capacity desse ser maior ou igual a zero")
  Integer capacity,
  String description,
  StatusTable status
) {}
