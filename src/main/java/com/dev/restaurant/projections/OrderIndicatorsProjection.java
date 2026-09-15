package com.dev.restaurant.projections;

import java.math.BigDecimal;

public interface OrderIndicatorsProjection {
    Long getTotalOrders();
    BigDecimal getTotalRevenue();
    BigDecimal getAverageOrderValue();
}
