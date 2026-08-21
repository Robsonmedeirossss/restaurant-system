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

import com.dev.restaurant.dtos.requests.creates.ProductOrderRequest;
import com.dev.restaurant.dtos.requests.updates.ProductOrderUpdate;
import com.dev.restaurant.dtos.responses.ProductOrderResponse;
import com.dev.restaurant.services.ProductOrderService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "Product orders", description = "Endpoint para os itens do pedido")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/restaurant/products-order")
public class ProductOrderController {
    private final ProductOrderService productOrderService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductOrderResponse> findAll() {
        return this.productOrderService.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductOrderResponse findById(@PathVariable Long id) {
        return this.productOrderService.findByid(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductOrderResponse create(@Valid @RequestBody ProductOrderRequest request) {
        return this.productOrderService.create(request);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductOrderResponse updateById(
        @PathVariable Long id,
        @Valid @RequestBody ProductOrderUpdate request
    ) {
        return this.productOrderService.updateById(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteById(@PathVariable Long id) {
        this.productOrderService.deleteById(id);
    }
}
