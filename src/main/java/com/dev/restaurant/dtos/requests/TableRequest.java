package com.dev.restaurant.dtos.requests;

import com.dev.restaurant.enums.StatusTable;

public record TableRequest(
  Integer number,
  Integer capacity,
  String description,
  StatusTable status
) {}
