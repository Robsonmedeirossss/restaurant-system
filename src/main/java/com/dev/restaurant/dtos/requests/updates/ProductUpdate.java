package com.dev.restaurant.dtos.requests.updates;

import java.math.BigDecimal;

import com.dev.restaurant.entities.Product;

import jakarta.validation.constraints.PositiveOrZero;

public record ProductUpdate(
    String name,

    String description,

    @PositiveOrZero(message = "Campo price deve ser maior ou igual a zero")
    BigDecimal price,

    Boolean available,
    
    @PositiveOrZero(message = "Campo preparationMinutes desse ser maior ou igual a zero")
    Integer preparationMinutes,
    
    @PositiveOrZero(message = "Campo stock desse ser maior ou igual a zero")
    Integer stock,
    
    @PositiveOrZero(message = "Campo categoryId desse ser um número maior ou igual a zero")
    Long categoryId
) {

    public Product merge(Product product) {
        if(name() != null) {
            product.setName(name());
        }

        if(description() != null) {
            product.setDescription(description());
        }
        
        if(price() != null) {
            product.setPrice(price());
        }

        if(available() != null) {
            product.setAvailable(available());
        }

        if(preparationMinutes() != null) {
            product.setPreparationMinutes(preparationMinutes());
        }

        if(stock() != null) {
            product.setStock(stock());
        }

        return product;
    }

}