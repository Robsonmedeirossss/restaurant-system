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

import com.dev.restaurant.dtos.requests.creates.BillingRequest;
import com.dev.restaurant.dtos.requests.updates.BillingUpdate;
import com.dev.restaurant.dtos.responses.BillingResponse;
import com.dev.restaurant.services.BillingService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "billings", description = "Endpoint para gerenciar as contas dos restaurantes")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/restaurant/billings")
public class BillingController {

    private final BillingService billingService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<BillingResponse> findAll() {
        return this.billingService.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BillingResponse findById(@PathVariable Long id) {
        return this.billingService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BillingResponse create(@Valid @RequestBody BillingRequest request) {
        return this.billingService.create(request);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BillingResponse updateById(
        @PathVariable Long id,
        @Valid @RequestBody BillingUpdate request 
    ) {    
        return this.billingService.updateById(id, request);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        this.billingService.deleteById(id);
    }

}
