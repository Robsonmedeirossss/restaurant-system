package com.payment.service.services;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.payment.service.dto.PaymentRequest;
import com.payment.service.dto.PaymentResponse;
import com.payment.service.enums.StatusPayment;

import io.awspring.cloud.sqs.operations.SqsTemplate;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor  
public class ProcessPaymentService {

    private final SqsTemplate sqsTemplate;
    @Value("${spring.cloud.aws.sqs.response-queue}")
    private String queue;
    

    public void process(PaymentRequest request) {
        if(request.amount().signum() > 0) {
            this.sqsTemplate.send(
                queue, 
                new PaymentResponse(
                    request.paymentId(),
                    request.orderId(),
                    request.amount(),
                    StatusPayment.APPROVED,
                    UUID.randomUUID(),
                    LocalDateTime.now()
                )
            );

            return;
        }

        this.sqsTemplate.send(
            this.queue,
            new PaymentResponse(
                request.paymentId(), 
                request.orderId(),
                request.amount(),
                StatusPayment.REFUSED,
                UUID.randomUUID(),
                null
            )
        );
    }

}
