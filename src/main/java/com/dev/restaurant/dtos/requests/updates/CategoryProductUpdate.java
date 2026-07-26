package com.dev.restaurant.dtos.requests.updates;

import com.dev.restaurant.entities.CategoryProduct;

public record CategoryProductUpdate(
    String name,
    Boolean available
) {

     public CategoryProduct merge(CategoryProduct categoryProduct) {

    if(name != null) {
      categoryProduct.setName(name);
    }

    if(available != null) {
      categoryProduct.setAvailable(available);
    }

    return categoryProduct;
  }

}
