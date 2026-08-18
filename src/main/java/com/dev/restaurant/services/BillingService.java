package com.dev.restaurant.services;

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

    public BillingResponse create(BillingRequest request) {

        Order order = this.orderService.findEntityById(request.orderId());

        return BillingMapper.toResponse(
            this.billingRepository.save(BillingMapper.toEntity(request, order))
        );
    }

    public BillingResponse updateById(Long id, BillingUpdate request) {
        Billing billing = this.findEntityById(id);

        return BillingMapper.toResponse(
            this.billingRepository.save(request.merge(billing))
        );
    }

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
}
