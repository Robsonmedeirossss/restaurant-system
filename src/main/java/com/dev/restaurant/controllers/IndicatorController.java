package com.dev.restaurant.controllers;

import java.time.LocalDate;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dev.restaurant.dtos.responses.indicators.IndicatorResponse;
import com.dev.restaurant.services.IndicatorService;

import lombok.RequiredArgsConstructor;

@RestController 
@RequestMapping("/v1/restaurant/indicators")
@RequiredArgsConstructor 
public class IndicatorController {

    private final IndicatorService indicatorService;

    @GetMapping
    public IndicatorResponse getOrderIndicators(
        @RequestParam(required = false) LocalDate from,
        @RequestParam(required = false) LocalDate to
    ) {
        return this.indicatorService.getOrderIndicators(from, to);
    }
    
}
