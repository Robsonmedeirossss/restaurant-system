package com.dev.restaurant.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dev.restaurant.entities.RestaurantTable;

public interface RestaurantTableRepository extends JpaRepository<RestaurantTable, Long> {
  
}
