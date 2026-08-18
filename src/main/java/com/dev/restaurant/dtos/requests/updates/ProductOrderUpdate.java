package com.dev.restaurant.dtos.requests.updates;

import com.dev.restaurant.entities.Order;
import com.dev.restaurant.entities.Product;
import com.dev.restaurant.entities.ProductOrder;
import com.dev.restaurant.enums.StatusProductOrder;

import jakarta.validation.constraints.PositiveOrZero;

public record ProductOrderUpdate(
    @PositiveOrZero(message = "Campo quantity deve ser maior ou igual a zero")
    Integer quantity,
    String observation,
    StatusProductOrder status,
    Long productId,
    Long orderId

) {
    public ProductOrder merge(
        ProductOrder productOrder,
        Product product,
        Order order
    ) {

        if(quantity() != null) {
            productOrder.setQuantity(quantity());
        }

        if(observation() != null) {
            productOrder.setObservation(observation());
        }

        if(status() != null) {
            productOrder.setStatus(status());
        }

        if(productId() != null) {
            productOrder.setProduct(product);
        }

        if(orderId() != null) {
            productOrder.setOrder(order);
        }

        return productOrder;
    }
}
