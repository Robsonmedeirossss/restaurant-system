package com.dev.restaurant.dtos.mappers;

import java.time.LocalDateTime;

import com.dev.restaurant.dtos.requests.creates.ProductRequest;
import com.dev.restaurant.dtos.responses.ProductResponse;
import com.dev.restaurant.entities.CategoryProduct;
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
            .createdAt(product.getCreatedAt())
            .build();
  }

  public static Product toEntity(ProductRequest product, CategoryProduct category) {
    return Product.builder()
            .name(product.name())
            .description(product.description())
            .price(product.price())
            .available(product.available() != null ? product.available() : true)
            .preparationMinutes(product.preparationMinutes())
            .stock(product.stock() != null ? product.stock() : 0)
            .categoryProduct(category)
            .createdAt(LocalDateTime.now())
            .build();
  }
}
