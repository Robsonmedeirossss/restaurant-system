package com.dev.restaurant.services;

import org.springframework.stereotype.Service;

import com.dev.restaurant.dtos.mappers.CategoryProductMapper;
import com.dev.restaurant.dtos.requests.CategoryProductRequest;
import com.dev.restaurant.dtos.responses.CategoryProductResponse;
import com.dev.restaurant.entities.CategoryProduct;
import com.dev.restaurant.exceptions.BusinessRuleException;
import com.dev.restaurant.repositories.CategoryProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryProductService {
  private final CategoryProductRepository categoryProductRepository;

  public CategoryProductResponse create(CategoryProductRequest request) {
    return CategoryProductMapper.toResponse(
      this.categoryProductRepository.save(
        CategoryProductMapper.toEntity(request)
      )
    );
  }

  public CategoryProductResponse findById(Long id) {
    return CategoryProductMapper.toResponse(
      this.findEntityById(id)
    );
  }

  public void deleteById(Long id) {
    this.categoryProductRepository.deleteById(id);
  }

  private CategoryProduct findEntityById(Long id) {
    return this.categoryProductRepository.findById(id)
      .orElseThrow(() -> new BusinessRuleException(
        String.format("Nenhuma categoria encontrada para o id %s", id)
      ));
  }
}
