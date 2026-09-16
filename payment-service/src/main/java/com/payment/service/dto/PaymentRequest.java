package com.payment.service.dto;

import java.math.BigDecimal;

import com.payment.service.enums.PaymentType;


public record PaymentRequest(
    Long paymentId,
    Long orderId,
    BigDecimal amount,
    PaymentType paymentType
) {}
