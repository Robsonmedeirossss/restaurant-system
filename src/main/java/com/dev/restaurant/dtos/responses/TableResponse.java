package com.dev.restaurant.dtos.responses;

import java.time.LocalDateTime;

import com.dev.restaurant.enums.StatusTable;

import lombok.Builder;

@Builder
public record TableResponse(
  Long id,
  Integer number,
  Integer capacity,
  String description,
  StatusTable status,
  LocalDateTime createdAt
) {}
