package com.dev.restaurant.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.dev.restaurant.dtos.requests.creates.OrderRequest;
import com.dev.restaurant.dtos.requests.creates.PaymentRequest;
import com.dev.restaurant.dtos.requests.creates.ProductOrderRequest;
import com.dev.restaurant.dtos.requests.filters.ProductOrderFilter;
import com.dev.restaurant.dtos.requests.updates.OrderUpdate;
import com.dev.restaurant.dtos.requests.updates.ProductOrderStatusRequest;
import com.dev.restaurant.dtos.requests.updates.ProductOrderUpdate;
import com.dev.restaurant.dtos.responses.OrderResponse;
import com.dev.restaurant.dtos.responses.PageProductOrderResponse;
import com.dev.restaurant.dtos.responses.PaymentResponse;
import com.dev.restaurant.dtos.responses.ProductOrderResponse;
import com.dev.restaurant.services.OrderService;
import com.dev.restaurant.services.PaymentService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Tag(name = "Orders", description = "Endpoint dos pedidos do restaurante")
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/restaurant/orders")
@Slf4j
public class OrderController {

    private final OrderService orderService;
    private final PaymentService paymentService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<OrderResponse> findAll() {
        return this.orderService.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OrderResponse findById(@PathVariable Long id) {
        return this.orderService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponse create(@Valid @RequestBody OrderRequest request) {
        System.out.println(String.format("O id chegou é: %s", request.tableId()));

        return this.orderService.create(request);
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{id}")
    public OrderResponse updateById(
        @PathVariable Long id,
        @Valid @RequestBody OrderUpdate request
    ) {
        return this.orderService.updateById(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        this.orderService.deleteById(id);
    }

    @PostMapping("/{orderId}/products-order")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductOrderResponse findAllProductOrderByOrderId(
        @PathVariable Long orderId,
        @Valid @RequestBody ProductOrderRequest request
    ) {
        return this.orderService.addProductOrder(orderId, request);
    }

    @GetMapping("/{orderId}/products-order")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductOrderResponse> findAllProductOrderByOrderId(@PathVariable Long orderId) {
        return this.orderService.findAllProductsOrderByOrderId(orderId);
    }

    @GetMapping("/products-order")
    @ResponseStatus(HttpStatus.OK)
    public PageProductOrderResponse findAllProductsOrder(
        ProductOrderFilter requestFilters
    ) {
        return this.orderService.findAllProductOrder(requestFilters);
    }


    @GetMapping("/{orderId}/products-order/{productOrderId}")
    @ResponseStatus(HttpStatus.OK)
    public ProductOrderResponse findByProductOrderById(
        @PathVariable Long orderId,
        @PathVariable Long productOrderId
    ) {
        return this.orderService.findProductOrderById(orderId, productOrderId);
    }

    @PatchMapping("/{orderId}/products-order/{productOrderId}")
    @ResponseStatus(HttpStatus.OK)
    public ProductOrderResponse updateProductOrderById(
        @PathVariable Long orderId,
        @PathVariable Long productOrderId,
        @Valid @RequestBody ProductOrderUpdate request 
    ) {
        return this.orderService.updateProductOrderById(orderId, productOrderId, request);
    }

    @PatchMapping("/{orderId}/products-order/{productOrderId}/status")
    @ResponseStatus(HttpStatus.OK)
    public ProductOrderResponse changeStatusById(
        @PathVariable Long orderId,
        @PathVariable Long productOrderId,
        @Valid @RequestBody ProductOrderStatusRequest statusRequest
    ) {
        return this.orderService.changeStatusById(orderId, productOrderId, statusRequest);
    }

    @DeleteMapping("/{orderId}/products-order/{productOrderId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateProductOrderById(
        @PathVariable Long orderId,
        @PathVariable Long productOrderId
    ) {
        this.orderService.deleteProductOrderById(orderId, productOrderId);
    }

    @PostMapping("/{orderId}/payment")
    @ResponseStatus(HttpStatus.OK)
    public PaymentResponse paymentByOrderId(@Valid @RequestBody PaymentRequest request) {
        return paymentService.process(request);
    }
    
}
