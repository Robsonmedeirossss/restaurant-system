package com.dev.restaurant.dtos.mappers;

import com.dev.restaurant.dtos.requests.creates.CategoryProductRequest;
import com.dev.restaurant.dtos.responses.CategoryProductResponse;
import com.dev.restaurant.entities.CategoryProduct;

public class CategoryProductMapper{
  public static CategoryProductResponse toResponse(CategoryProduct categoryProduct) {
    return CategoryProductResponse.builder()
            .id(categoryProduct.getId())
            .name(categoryProduct.getName())
            .available(categoryProduct.getAvailable())
            .build();
  }

  public static CategoryProduct toEntity(CategoryProductRequest categoryProductRequest) {
    return CategoryProduct.builder()
            .name(categoryProductRequest.name())
            .available(categoryProductRequest.available() != null ? categoryProductRequest.available() : true)
            .build();
  }
}

