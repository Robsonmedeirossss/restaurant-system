package com.dev.restaurant.dtos.mappers;

import com.dev.restaurant.dtos.responses.BillingResponse;
import com.dev.restaurant.entities.Billing;

public class BillingMapper {
  public static BillingResponse toResponse(Billing billing) {
    return BillingResponse.builder()
            .id(billing.getId())
            .orderId(billing.getOrder().getId())
            .subtotal(billing.getSubtotal())
            .discount(billing.getDiscount())
            .serviceTax(billing.getServiceTax())
            .total(billing.getTotal())
            .createdAt(billing.getCreatedAt())
            .closedAt(billing.getClosedAt())
            .build();
  }
}
