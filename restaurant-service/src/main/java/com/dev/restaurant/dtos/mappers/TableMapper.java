package com.dev.restaurant.dtos.mappers;

import com.dev.restaurant.dtos.requests.creates.TableRequest;
import com.dev.restaurant.dtos.responses.TableResponse;
import com.dev.restaurant.entities.RestaurantTable;
import com.dev.restaurant.enums.StatusTable;

public class TableMapper {
  public static TableResponse toResponse(RestaurantTable table) {
    return TableResponse.builder()
            .id(table.getId())
            .capacity(table.getCapacity())
            .description(table.getDescription())
            .number(table.getNumber())
            .status(table.getStatus())
            .createdAt(table.getCreatedAt())
            .build();
  }

  public static RestaurantTable toEntity(TableRequest table) {
    return RestaurantTable.builder()
            .capacity(table.capacity() != null ? table.capacity() : 4)
            .number(table.number())
            .description(table.description())
            .status(table.status() != null ? table.status() : StatusTable.FREE)
            .build();
  }

}
