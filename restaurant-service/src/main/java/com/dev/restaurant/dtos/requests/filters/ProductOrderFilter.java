package com.dev.restaurant.dtos.requests.filters;

import java.util.List;

import com.dev.restaurant.enums.StatusProductOrder;

import lombok.Builder;

@Builder
public record ProductOrderFilter(
    Integer page,
    Integer size,
    List<StatusProductOrder> status,
    String orderBy,
    String direction
) {

    public ProductOrderFilter setDefault() {
        return ProductOrderFilter
            .builder()
            .page(this.page() == null ? 0 : this.page())
            .size(this.size() == null? 5 : this.size())
            .orderBy(this.orderBy() == null ? "createdAt" : this.orderBy())
            .direction(this.direction() == null ? "desc" : this.direction())
            .status(this.status())
            .build();
    }
}
