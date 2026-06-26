package com.dev.restaurant.dtos.requests;

import com.dev.restaurant.entities.RestaurantTable;
import com.dev.restaurant.enums.StatusTable;

public record TableUpdate( 
  Integer capacity,
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
