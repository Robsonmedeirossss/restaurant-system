package com.dev.restaurant.services;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.dev.restaurant.dtos.mappers.ProductMapper;
import com.dev.restaurant.dtos.requests.creates.ProductRequest;
import com.dev.restaurant.dtos.requests.updates.ProductUpdate;
import com.dev.restaurant.dtos.responses.ProductResponse;
import com.dev.restaurant.entities.CategoryProduct;
import com.dev.restaurant.entities.Product;
import com.dev.restaurant.exceptions.BusinessRuleException;
import com.dev.restaurant.repositories.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryProductService categoryProductService;

    public Set<ProductResponse> findAll() {
        return this.productRepository.findAll()
            .stream()
            .map(ProductMapper::toResponse)
            .collect(Collectors.toSet());
    }

    public ProductResponse findById(Long id) {
        return ProductMapper.toResponse(
            this.findEntityById(id)
        );
    }

    public ProductResponse create(ProductRequest request) {

        CategoryProduct category = this.categoryProductService.findEntityById(request.categoryId());

        return ProductMapper.toResponse(this.productRepository.save(
                ProductMapper.toEntity(request, category)
            )
        );
    }

    public ProductResponse updateById(Long id, ProductUpdate request) {

        if(request.categoryId() != null) {
            this.categoryProductService.findById(request.categoryId());
        }

        Product product = this.findEntityById(id);

        return ProductMapper.toResponse(
            this.productRepository.save(request.merge(product))
        );
    }

    public void deleteById(Long id) {
        this.productRepository.deleteById(id);
    }


    private Product findEntityById (Long id) {
        return this.productRepository.findById(id)
            .orElseThrow(
                () -> new BusinessRuleException(
                    String.format("Nenhum produto encontrado para o id %s", id)
                )
        );
    } 
}
