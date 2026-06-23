package com.dev.restaurant.dtos.requests;

import java.time.LocalDateTime;

import com.dev.restaurant.enums.StatusOrder;

public record OrderRequest(
  StatusOrder status,
  Long tableId,
  LocalDateTime openingDate,
  String observation
) {} 
