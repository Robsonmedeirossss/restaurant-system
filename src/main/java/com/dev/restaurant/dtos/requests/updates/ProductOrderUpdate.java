package com.dev.restaurant.dtos.requests.updates;

import com.dev.restaurant.entities.ProductOrder;

import jakarta.validation.constraints.PositiveOrZero;

public record ProductOrderUpdate(
    @PositiveOrZero(message = "Campo quantity deve ser maior ou igual a zero")
    Integer quantity,
    String observation
) {
    public ProductOrder merge(
        ProductOrder productOrder
    ) {

        if(quantity() != null) {
            productOrder.setQuantity(quantity());
        }

        if(observation() != null) {
            productOrder.setObservation(observation());
        }

        return productOrder;
    }
}
