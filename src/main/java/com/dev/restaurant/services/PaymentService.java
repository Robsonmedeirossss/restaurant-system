package com.dev.restaurant.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.dev.restaurant.dtos.mappers.PaymentMapper;
import com.dev.restaurant.dtos.requests.creates.PaymentMessageDTO;
import com.dev.restaurant.dtos.requests.creates.PaymentRequest;
import com.dev.restaurant.dtos.responses.PaymentResponse;
import com.dev.restaurant.entities.Billing;
import com.dev.restaurant.entities.Order;
import com.dev.restaurant.repositories.PaymentRepository;

import io.awspring.cloud.sqs.operations.SqsTemplate;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor 
public class PaymentService {
    
    private final PaymentRepository paymentRepository;
    private final OrderService orderService;
    private final BillingService billingService;
    private final SqsTemplate sqsTemplate;

    @Value("${spring.cloud.sqs.queue-request}")
    private String paymentRequestQueue;



    @Transactional
    public PaymentResponse process(PaymentRequest request) {

        Order order = this.orderService.findEntityById(request.orderId());
        Billing billing = this.billingService.findBillingByOrderId(request.orderId());

        PaymentResponse paymentResponse = PaymentMapper.toResponse(
            this.paymentRepository.save(
                PaymentMapper.toEntity(request, order, billing)
            )
        );

        this.sqsTemplate.send(
            this.paymentRequestQueue,
            new PaymentMessageDTO(
                paymentResponse.id(),
                order.getId(),
                paymentResponse.value(),
                paymentResponse.paymentType()
            )
        );

        return paymentResponse;
    }

}
