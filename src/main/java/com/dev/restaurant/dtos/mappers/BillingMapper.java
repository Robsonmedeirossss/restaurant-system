package com.dev.restaurant.dtos.mappers;

import java.time.LocalDateTime;

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

  public static Billing toEntity(BillingRequest billing, Order order) {
    return  Billing.builder()
              .order(order)
              .discount(billing.discount() != null ? billing.discount() : 0)
              .serviceTax(billing.serviceTax() != null ? billing.serviceTax() : 0)
              .closedAt(billing.closedAt() != null 
                ? billing.closedAt() 
                : LocalDateTime.now()
              )
              .createdAt(LocalDateTime.now())
              .build();
  }
}
