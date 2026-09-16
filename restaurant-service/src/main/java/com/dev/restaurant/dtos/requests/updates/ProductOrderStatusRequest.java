package com.dev.restaurant.dtos.requests.updates;

import com.dev.restaurant.enums.StatusProductOrder;

import jakarta.validation.constraints.NotNull;

public record ProductOrderStatusRequest(
    @NotNull(message = "Campo status não pode ser nulo")
    StatusProductOrder status
) {
    
}
