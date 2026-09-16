package com.dev.restaurant.projections;

import com.dev.restaurant.enums.StatusOrder;

public interface OrdersByStatusProjection {
    StatusOrder getStatus();
    Long getQuantity();
}
