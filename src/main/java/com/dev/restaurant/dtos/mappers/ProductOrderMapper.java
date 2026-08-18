package com.dev.restaurant.dtos.mappers;

import com.dev.restaurant.dtos.requests.creates.ProductOrderRequest;
import com.dev.restaurant.dtos.responses.ProductOrderResponse;
import com.dev.restaurant.entities.Order;
import com.dev.restaurant.entities.Product;
import com.dev.restaurant.entities.ProductOrder;
import com.dev.restaurant.enums.StatusProductOrder;

public class ProductOrderMapper {
  public static ProductOrderResponse toResponse(
    ProductOrder productOrder
  ) {
    return ProductOrderResponse.builder()
            .id(productOrder.getId())
            .productId(productOrder.getProduct().getId())
            .orderId(productOrder.getOrder().getId())
            .unityPrice(productOrder.getUnityPrice())
            .quantity(productOrder.getQuantity())
            .observation(productOrder.getObservation())
            .preparationStartAt(productOrder.getPreparationStartAt())
            .preparationEndAt(productOrder.getPreparationEndAt())
            .status(productOrder.getStatus())
            .createdAt(productOrder.getCreatedAt())
            .build();
  }

  public static ProductOrder toEntity(
    ProductOrderRequest productOrderRequest,
    Product product,
    Order order
  ) {
    return ProductOrder.builder()
            .product(product)
            .order(order)
            .quantity(productOrderRequest.quantity())
            .observation(productOrderRequest.observation())
            .status(
              productOrderRequest.status() != null 
              ? productOrderRequest.status() 
              : StatusProductOrder.PENDING
            )
            .build();
  }
}
