package com.dev.restaurant.dtos.requests.creates;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.dev.restaurant.enums.StatusPayment;

public record PaymentResultMessage(
    Long paymentId,
    Long orderId,
    BigDecimal amount,
    StatusPayment statusPayment,
    String externalTransactionCode,
    LocalDateTime paidIn
) {}
