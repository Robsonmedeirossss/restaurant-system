package com.dev.restaurant.controllers;

import java.util.Set;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dev.restaurant.dtos.requests.CategoryProductRequest;
import com.dev.restaurant.dtos.requests.updates.CategoryProductUpdate;
import com.dev.restaurant.dtos.responses.CategoryProductResponse;
import com.dev.restaurant.services.CategoryProductService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;


@RestController
@RequestMapping("/v1/restaurant/categories-products")
@RequiredArgsConstructor
public class CategoryProductController {

    private final CategoryProductService categoryProductService;
    
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Set<CategoryProductResponse> findAll() {
        return this.categoryProductService.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CategoryProductResponse findById(@PathVariable Long id) {
        return this.categoryProductService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryProductResponse create(@Valid @RequestBody CategoryProductRequest request) {
        return this.categoryProductService.create(request);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CategoryProductResponse updateById(
        @PathVariable Long id,
        @Valid @RequestBody CategoryProductUpdate request
    ) {
        return this.categoryProductService.updateById(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        this.categoryProductService.deleteById(id);
    }
    
}
