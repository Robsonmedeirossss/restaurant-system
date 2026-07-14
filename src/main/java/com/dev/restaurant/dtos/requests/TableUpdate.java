package com.dev.restaurant.dtos.requests;

import com.dev.restaurant.entities.RestaurantTable;
import com.dev.restaurant.enums.StatusTable;

import jakarta.validation.constraints.Size;

public record TableUpdate(
  Integer capacity,
  @Size(min = 1, max = 1500, message = "Description deve ter entre 1 e 1500 caracteres")
  String description,
  StatusTable status
) {

  public RestaurantTable merge(RestaurantTable restaurantTable) {
    if(this.capacity() != null) {
      restaurantTable.setCapacity(this.capacity);
    }

    if(this.description() != null) {
      restaurantTable.setDescription(this.description);
    }

    if(this.status() != null) {
      restaurantTable.setStatus(this.status);
    }

    return restaurantTable;
  }
}
