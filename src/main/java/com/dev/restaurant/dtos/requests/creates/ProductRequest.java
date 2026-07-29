package com.dev.restaurant.dtos.requests.creates;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;

@Builder
public record ProductRequest(
  @NotBlank(message = "Campo name não pode ser vazio")
  String name,
  @NotBlank(message = "Campo description não pode ser vazio")
  String description,
  @NotNull(message = "Campo name não pode ser vazio")
  @PositiveOrZero(message = "Campo price desse ser maior ou igual a zero")
  BigDecimal price,
  Boolean available,
  @PositiveOrZero(message = "Campo preparationMinutes desse ser maior ou igual a zero")
  Integer preparationMinutes,
  @PositiveOrZero(message = "Campo stock desse ser maior ou igual a zero")
  Integer stock,
  @NotNull(message = "Campo categoryId não pode ser nulo")
  @PositiveOrZero(message = "Campo categoryId desse ser um número maior ou igual a zero")
  Long categoryId
) {}
