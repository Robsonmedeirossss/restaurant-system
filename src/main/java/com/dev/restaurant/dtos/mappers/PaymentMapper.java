package com.dev.restaurant.dtos.mappers;

import com.dev.restaurant.dtos.requests.PaymentRequest;
import com.dev.restaurant.dtos.responses.PaymentResponse;
import com.dev.restaurant.entities.Order;
import com.dev.restaurant.entities.Payment;

public class PaymentMapper {
  public static PaymentResponse toResponse(Payment payment) {
    return PaymentResponse.builder()
            .id(payment.getId())
            .orderId(payment.getOrder().getId())
            .value(payment.getValue())
            .paymentType(payment.getPaymentType())
            .status(payment.getStatus())
            .externalTransactionCode(payment.getExternalTransactionCode())
            .paymentDate(payment.getPaymentDate())
            .createdAt(payment.getCreatedAt())
            .build();
  }

  public static Payment toEntity(PaymentRequest payment, Order order) {
    return Payment.builder()
            .order(order)
            .value(payment.value())
            .paymentType(payment.paymentType())
            .status(payment.status())
            .externalTransactionCode(payment.externalTransactionCode())
            .paymentDate(payment.paymentDate())
            .createdAt(payment.createdAt())
            .build();
  }
}
