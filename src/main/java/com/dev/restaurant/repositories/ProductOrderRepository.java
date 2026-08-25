package com.dev.restaurant.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dev.restaurant.entities.ProductOrder;

public interface ProductOrderRepository extends JpaRepository<ProductOrder, Long>{   
}