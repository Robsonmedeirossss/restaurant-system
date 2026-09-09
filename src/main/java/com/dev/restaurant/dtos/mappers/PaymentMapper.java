package com.dev.restaurant.dtos.mappers;

import java.time.LocalDateTime;

import com.dev.restaurant.dtos.requests.creates.PaymentRequest;
import com.dev.restaurant.dtos.responses.PaymentResponse;
import com.dev.restaurant.entities.Billing;
import com.dev.restaurant.entities.Order;
import com.dev.restaurant.entities.Payment;
import com.dev.restaurant.enums.StatusPayment;

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

  public static Payment toEntity(PaymentRequest payment, Order order, Billing billing) {
    return Payment.builder()
            .order(order)
            .paymentType(payment.paymentType())
            .status(StatusPayment.PENDING)
            .paymentDate(LocalDateTime.now())
            .value(billing.getTotal())
            .createdAt(LocalDateTime.now())
            .build();
  }
}
