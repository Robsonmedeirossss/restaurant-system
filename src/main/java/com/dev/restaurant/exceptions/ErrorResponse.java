package com.dev.restaurant.exceptions;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;
import lombok.Data;
import lombok.Setter;

@Data
@Builder
@Setter
public class ErrorResponse {
  private final LocalDateTime timestamp;
  private final Integer status;
  private final String error;
  private final List<Error> errorList;
}
