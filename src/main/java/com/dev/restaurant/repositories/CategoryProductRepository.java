package com.dev.restaurant.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dev.restaurant.entities.CategoryProduct;

@Repository
public interface CategoryProductRepository extends JpaRepository<CategoryProduct, Long> {
  
}
