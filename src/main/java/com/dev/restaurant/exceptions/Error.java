package com.dev.restaurant.exceptions;

import lombok.Data;

@Data
public class Error {
  private final String field;
  private final String message;
}
