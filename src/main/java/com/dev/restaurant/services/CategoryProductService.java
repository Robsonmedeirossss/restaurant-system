package com.dev.restaurant.services;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.dev.restaurant.dtos.mappers.CategoryProductMapper;
import com.dev.restaurant.dtos.requests.creates.CategoryProductRequest;
import com.dev.restaurant.dtos.requests.updates.CategoryProductUpdate;
import com.dev.restaurant.dtos.responses.CategoryProductResponse;
import com.dev.restaurant.entities.CategoryProduct;
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

  public Set<CategoryProductResponse> findAll() {
    return this.categoryProductRepository.findAll()
      .stream()
      .map(CategoryProductMapper::toResponse)
      .collect(Collectors.toSet());
  }

  public void deleteById(Long id) {
    this.categoryProductRepository.deleteById(id);
  }

  public CategoryProductResponse updateById(Long id, CategoryProductUpdate request) {
    CategoryProduct categoryProduct = this.findEntityById(id);

    return CategoryProductMapper.toResponse(
      this.categoryProductRepository.save(request.merge(categoryProduct))
    );
  }

  protected CategoryProduct findEntityById(Long id) {
    return this.categoryProductRepository.findById(id)
      .orElseThrow(() -> new ResponseStatusException(
        HttpStatus.NOT_FOUND,
        String.format("Nenhuma categoria encontrada para o id %s", id)
      ));
  }
}
