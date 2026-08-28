package com.dev.restaurant.services;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.dev.restaurant.dtos.mappers.BillingMapper;
import com.dev.restaurant.dtos.requests.creates.BillingRequest;
import com.dev.restaurant.dtos.requests.updates.BillingUpdate;
import com.dev.restaurant.dtos.responses.BillingResponse;
import com.dev.restaurant.entities.Billing;
import com.dev.restaurant.entities.Order;
import com.dev.restaurant.repositories.BillingRepository;
import com.dev.restaurant.repositories.OrderRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BillingService {

    private final BillingRepository billingRepository;
    private final OrderService orderService;


    public List<BillingResponse> findAll() {
        return this.billingRepository.findAll()
            .stream()
            .map(BillingMapper::toResponse)
            .toList();
    }

    public BillingResponse findById(Long id) {
        return BillingMapper.toResponse(
            this.findEntityById(id)
        );
    }

    @Transactional
    public BillingResponse create(BillingRequest request) {

        Order order = this.orderService.findEntityById(request.orderId());

        if(this.billingOrderAlreadyExists(order.getId())) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
            String.format("Já existe uma conta para o pedido de id %s", order.getId()));
        }

        if(order.getProductOrders().isEmpty()) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Não é possível fechar a conta para um pedido sem itens"
            );
        }

        if(order.getStatus().cantBeClosed()) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Não é possível fechar a conta para um pedido com status PENDING ou CANCELED, pedido deve estar com status DOING"
            );
        }

        order.getProductOrders().stream()
            .forEach(productOrder -> {
                if(!productOrder.getStatus().isDone()) {
                    throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Todos os itens do pedido devem estar com status DONE"
                    );
                }
            });
        
        
        Billing billing = BillingMapper.toEntity(request, order);

        BigDecimal subtotal = this.orderService.getSubtotal(order.getId());  
        BigDecimal total = this.getTotal(billing, subtotal);


        order.markOrderAsDone();

        billing.setSubtotal(subtotal);
        billing.setTotal(total);
        

        return BillingMapper.toResponse(
            this.billingRepository.save(billing)
        );
    }

    @Transactional
    public BillingResponse updateById(Long id, BillingUpdate request) {
        Billing billing = this.findEntityById(id);

        return BillingMapper.toResponse(
            this.billingRepository.save(request.merge(billing))
        );
    }

    @Transactional
    public void deleteById(Long id) {
        this.findEntityById(id);
        this.billingRepository.deleteById(id);
    }

    private Billing findEntityById(Long id) {
        return this.billingRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                String.format("Nenhuma conta encontrada para o id %s", id)
             ));
    }

    private boolean billingOrderAlreadyExists(Long id) {
        return this.billingRepository.findBillingByOrderId(id).isPresent();
    }

    private BigDecimal getTotal(Billing billing, BigDecimal subtotal) {

        BigDecimal serviceTaxValue = new BigDecimal("0");
        BigDecimal discountValue = new BigDecimal("0");

          if(billing.getServiceTax() != 0) {
            serviceTaxValue = new BigDecimal(
                billing.getServiceTax().toString())
                .divide(new BigDecimal("100"))
                .multiply(subtotal)
                .setScale(2, RoundingMode.HALF_UP);
        }

        if(billing.getDiscount() != 0) {
            discountValue = new BigDecimal(billing.getDiscount().toString())
                .divide(new BigDecimal("100"))
                .multiply(subtotal)
                .setScale(2, RoundingMode.HALF_UP);
        }

        return subtotal.subtract(discountValue)
            .add(serviceTaxValue)
            .setScale(2, RoundingMode.HALF_UP);
    }
}
