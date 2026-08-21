package com.dev.restaurant.dtos.requests.updates;

import java.time.LocalDateTime;

import com.dev.restaurant.entities.Order;
import com.dev.restaurant.enums.StatusOrder;

import jakarta.validation.constraints.PositiveOrZero;

public record OrderUpdate(
    StatusOrder status,
    @PositiveOrZero(message = "Campo tableId deve ser maior ou igual a zero")
    Long tableId,
    String observation,
    LocalDateTime openingDate,
    LocalDateTime closingDate
) {
    public Order merge(Order order) {
        if(status() != null) {
            order.setStatus(status());
        }

        if(observation() != null) {
            order.setObservation(observation());
        }

        if(openingDate() != null) {
            order.setOpeningDate(openingDate());
        }

        if(closingDate() != null) {
            order.setClosingDate(closingDate());
        }

        return order;
    }
} 