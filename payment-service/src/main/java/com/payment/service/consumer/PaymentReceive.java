package com.payment.service.consumer;

import org.springframework.stereotype.Component;

import com.payment.service.dto.PaymentRequest;
import com.payment.service.services.ProcessPaymentService;

import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor 
@Slf4j 
public class PaymentReceive {

    private final ProcessPaymentService paymentService;

    @SqsListener("payment-request")
    public void receiveMessage(PaymentRequest message) {
        this.paymentService.process(message);
        log.info(message.toString());
    }

}
