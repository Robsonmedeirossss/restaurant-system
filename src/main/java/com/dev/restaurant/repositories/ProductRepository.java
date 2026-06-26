package com.dev.restaurant.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dev.restaurant.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
  
}
