package com.dev.restaurant.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.dev.restaurant.dtos.mappers.PaymentMapper;
import com.dev.restaurant.dtos.requests.creates.PaymentMessageDTO;
import com.dev.restaurant.dtos.requests.creates.PaymentRequest;
import com.dev.restaurant.dtos.requests.creates.PaymentResultMessage;
import com.dev.restaurant.dtos.responses.PaymentResponse;
import com.dev.restaurant.entities.Billing;
import com.dev.restaurant.entities.Order;
import com.dev.restaurant.entities.Payment;
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
    public PaymentResponse process(PaymentRequest request, Long orderId) {

        Order order = this.orderService.findEntityById(orderId);
        Billing billing = this.billingService.findBillingByOrderId(orderId);
        this.findPaymentByOrderId(order.getId());

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

    @Transactional 
    public void updatePayment(PaymentResultMessage resultMessage) {
        this.orderService.findEntityById(resultMessage.orderId());
        Payment payment = this.findEntityById(resultMessage.paymentId());

        payment.setStatus(resultMessage.statusPayment());
        payment.setExternalTransactionCode(resultMessage.externalTransactionCode());
        payment.setPaymentDate(resultMessage.paidIn());
        payment.setStatus(resultMessage.statusPayment());

        this.paymentRepository.save(payment);
    }

    private Payment findEntityById(Long id) {
        return this.paymentRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "PaymentId inexistente"
                )
            );
    }

    private Payment findPaymentByOrderId(Long orderId) {
        return this.paymentRepository.findByOrderId(orderId)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.CONFLICT,
                String.format("Já existe um pagamento para o pedido de id %d", orderId)
            ));
    }

}
