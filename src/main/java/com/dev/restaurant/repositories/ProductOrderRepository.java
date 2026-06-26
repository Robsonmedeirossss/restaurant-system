package com.dev.restaurant.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dev.restaurant.entities.ProductOrder;

@Repository
public interface ProductOrderRepository extends JpaRepository<ProductOrder, Long> {
  
}
