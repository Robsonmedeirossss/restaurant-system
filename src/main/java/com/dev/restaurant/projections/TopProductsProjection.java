package com.dev.restaurant.projections;

import java.math.BigDecimal;

public interface  TopProductsProjection {
    String getProductName();
    Long getQuantitySold();
    BigDecimal getRevenue();
}
