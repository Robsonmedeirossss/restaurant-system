package com.payment.service.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.payment.service.enums.StatusPayment;

import lombok.Builder;

@Builder 
public record PaymentResponse(
    Long paymentId,
    Long orderId,
    BigDecimal amount,
    StatusPayment statusPayment,
    UUID externalTransactionCode,
    LocalDateTime paidIn
){}
