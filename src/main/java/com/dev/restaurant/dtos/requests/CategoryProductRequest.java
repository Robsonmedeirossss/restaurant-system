package com.dev.restaurant.dtos.requests;

import jakarta.validation.constraints.NotBlank;

public record CategoryProductRequest(
  @NotBlank(message = "Campo name é obrigatório")
  String name,
  Boolean available
) {}
