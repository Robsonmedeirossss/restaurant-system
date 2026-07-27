package com.dev.restaurant.dtos.mappers;

import com.dev.restaurant.dtos.requests.creates.BillingRequest;
import com.dev.restaurant.dtos.responses.BillingResponse;
import com.dev.restaurant.entities.Billing;
import com.dev.restaurant.entities.Order;

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

  public static Billing toEntity(BillingRequest biliing, Order order) {
    return  Billing.builder()
              .subtotal(biliing.subtotal())
              .order(order)
              .discount(biliing.discount())
              .serviceTax(biliing.serviceTax())
              .build();
  }
}
