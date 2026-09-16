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
        if(this.name() != null) {
            product.setName(this.name());
        }

        if(this.description() != null) {
            product.setDescription(this.description());
        }
        
        if(this.price() != null) {
            product.setPrice(this.price());
        }

        if(this.available() != null) {
            product.setAvailable(this.available());
        }

        if(this.preparationMinutes() != null) {
            product.setPreparationMinutes(this.preparationMinutes());
        }

        if(this.stock() != null) {
            product.setStock(this.stock());
        }

        return product;
    }

}