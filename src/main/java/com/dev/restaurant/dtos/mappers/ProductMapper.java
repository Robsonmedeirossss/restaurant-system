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
            .imageUrl(product.getImageUrl())
            .categoryId(product.getCategoryProduct().getId())
            .createdAt(product.getCreatedAt())
            .build();
  }

  public static Product toEntity(ProductRequest product, CategoryProduct category, String imageUrl) {
    return Product.builder()
            .name(product.name())
            .description(product.description())
            .price(product.price())
            .available(product.available() != null ? product.available() : true)
            .preparationMinutes(product.preparationMinutes() != null ? product.preparationMinutes() : 30)
            .stock(product.stock() != null ? product.stock() : 0)
            .imageUrl(imageUrl)
            .categoryProduct(category)
            .createdAt(LocalDateTime.now())
            .build();
  }
}
