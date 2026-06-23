package com.dev.restaurant.dtos.mappers;

import com.dev.restaurant.dtos.requests.ProductRequest;
import com.dev.restaurant.dtos.responses.ProductResponse;
import com.dev.restaurant.entities.Product;

public class ProductMapper {
  public static ProductResponse toResponse(Product product) {
    return ProductResponse.builder()
            .id(product.getId())
            .name(product.getName())
            .description(product.getDescription())
            .price(product.getPrice())
            .available(product.getAvailable())
            .preparationMinutes(product.getPreparationMinutes())
            .stock(product.getStock())
            .categoryId(product.getCategoryProduct().getId())
            .build();
  }

  public static Product toEntity(ProductRequest product) {
    return Product.builder()
            .name(product.name())
            .description(product.description())
            .price(product.price())
            .available(product.available())
            .preparationMinutes(product.preparationMinutes())
            .stock(product.Stock())
            .build();
  }
}
