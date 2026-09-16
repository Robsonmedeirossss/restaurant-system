package com.dev.restaurant.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dev.restaurant.entities.RestaurantTable;

@Repository
public interface RestaurantTableRepository extends JpaRepository<RestaurantTable, Long> {
  @Query(value = """ 
    SELECT EXISTS(
      SELECT 1
      FROM restaurant_tables
      WHERE number = :number
    )
    """ ,nativeQuery = true)
  boolean existsByTableNumber(@Param("number") Integer number);
}
