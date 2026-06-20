package com.dev.restaurant.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dev.restaurant.entities.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
  
}
