package com.dev.restaurant.dtos.requests.creates;

import java.math.BigDecimal;

import com.dev.restaurant.enums.PaymentType;

public record PaymentMessageDTO(
    Long paymentId,
    Long orderId,
    BigDecimal amount,
    PaymentType paymentType
) {}
