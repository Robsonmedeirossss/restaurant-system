package com.dev.restaurant.aws;

import org.springframework.stereotype.Component;

import com.dev.restaurant.dtos.requests.creates.PaymentResultMessage;
import com.dev.restaurant.services.PaymentService;

import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.RequiredArgsConstructor;

@Component 
@RequiredArgsConstructor 
public class Consumer {

    private final PaymentService paymentService;

    @SqsListener("payment-response")
    public void paymentResponseMessage(PaymentResultMessage message) {
        System.out.println(message);
        this.paymentService.updatePayment(message);
    }
    
}
