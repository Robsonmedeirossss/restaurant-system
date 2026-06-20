package com.dev.restaurant.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dev.restaurant.entities.CategoryProduct;

public interface CategoryProductRepository extends JpaRepository<CategoryProduct, Long> {
  
}
