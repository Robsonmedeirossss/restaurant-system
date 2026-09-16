package com.dev.restaurant.controllers;

import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dev.restaurant.dtos.requests.creates.ProductRequest;
import com.dev.restaurant.dtos.requests.updates.ProductUpdate;
import com.dev.restaurant.dtos.responses.ProductResponse;
import com.dev.restaurant.services.ProductService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "Products", description = "Endpoint dos produtos do restaurante")
@RestController
@RequestMapping("/v1/restaurant/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Set<ProductResponse> findAll() {
        return this.productService.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductResponse findById(@PathVariable Long id) {
        return this.productService.findById(id);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponse create(
        @RequestPart(value = "image", required = false) MultipartFile image,
        @RequestPart("productData") @Valid ProductRequest request
    ) {
        return this.productService.create(request, image);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductResponse updateById(
        @PathVariable Long id,
        @Valid @RequestBody ProductUpdate request
    ) {
        return this.productService.updateById(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        this.productService.deleteById(id);
    }
}
