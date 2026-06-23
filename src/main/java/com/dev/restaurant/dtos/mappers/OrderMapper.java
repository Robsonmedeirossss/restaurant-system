package com.dev.restaurant.dtos.mappers;

import com.dev.restaurant.dtos.requests.OrderRequest;
import com.dev.restaurant.dtos.responses.OrderResponse;
import com.dev.restaurant.entities.Order;
import com.dev.restaurant.entities.RestaurantTable;

public class OrderMapper {
  public static OrderResponse toResponse(Order order) {
    return OrderResponse.builder()
            .id(order.getId())
            .status(order.getStatus())
            .tableId(order.getTable().getId())
            .openingDate(order.getOpeningDate())
            .closingDate(order.getClosingDate())
            .observation(order.getObservation())
            .createdAt(order.getCreatedAt())
            .build();
  }

  public static Order toEntity(OrderRequest order, RestaurantTable table) {
    return Order.builder()
            .status(order.status())
            .table(table)
            .openingDate(order.openingDate())
            .observation(order.observation())
            .build();
  }
}
