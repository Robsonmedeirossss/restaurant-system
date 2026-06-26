package com.dev.restaurant.dtos.responses;

import lombok.Builder;

@Builder
public record CategoryProductResponse(
  Long id,
  String name,
  Boolean available
) {}
