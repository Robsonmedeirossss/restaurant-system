package com.dev.restaurant.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dev.restaurant.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
  
}
