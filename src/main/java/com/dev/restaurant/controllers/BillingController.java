package com.dev.restaurant.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dev.restaurant.services.BillingService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "billings", description = "Endpoint para gerenciar as contas dos restaurantes")
@RestController
@RequestMapping("/v1/restaurant/billings")
@RequiredArgsConstructor
public class BillingController {
    private final BillingService billingService;


}
