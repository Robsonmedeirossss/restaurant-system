package com.dev.restaurant.exceptions;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class GlobalHandleException {

  @ExceptionHandler(ResponseStatusException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorResponse businessRuleException(ResponseStatusException e) {
    return ErrorResponse.builder()
            .timestamp(LocalDateTime.now())
            .status(e.getStatusCode().value())
            .error(e.getMessage())
            .errorList(List.of())
            .build();
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorResponse methodArgumentNotValidException(MethodArgumentNotValidException e) {

      List<Error> errors = List.of();

      e.getAllErrors().forEach((error) -> {
          String field = ((FieldError) error).getField();
          String message = error.getDefaultMessage();

          Error err = new Error(field, message);

          errors.add(err);
      });

      ErrorResponse errorResponse = ErrorResponse.builder()
      .timestamp(LocalDateTime.now())
      .status(HttpStatus.BAD_REQUEST.value())
      .error(e.getMessage())
      .errorList(errors)
      .build();

    return errorResponse;
  }

  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ErrorResponse globalException(Exception e) {
    return ErrorResponse.builder()
            .timestamp(LocalDateTime.now())
            .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
            .error(e.getMessage())
            .errorList(List.of())
            .build();
  }
}
