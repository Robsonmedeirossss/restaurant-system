package com.dev.restaurant.dtos.mappers;

import java.time.LocalDateTime;

import com.dev.restaurant.dtos.requests.creates.OrderRequest;
import com.dev.restaurant.dtos.responses.OrderResponse;
import com.dev.restaurant.entities.Order;
import com.dev.restaurant.entities.RestaurantTable;
import com.dev.restaurant.enums.StatusOrder;

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
            .status(order.status() != null ? order.status() : StatusOrder.PENDING)
            .table(table)
            .openingDate(order.openingDate() != null ? order.openingDate() : LocalDateTime.now())
            .observation(order.observation())
            .build();
  }
}
