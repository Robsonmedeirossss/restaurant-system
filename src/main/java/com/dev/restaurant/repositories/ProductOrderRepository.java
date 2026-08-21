package com.dev.restaurant.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dev.restaurant.entities.ProductOrder;

@Repository
public interface ProductOrderRepository extends JpaRepository<ProductOrder, Long> {
  @Query(value = """ 
    SELECT EXISTS (
        SELECT 1 FROM products_order
        WHERE product_id = :productId
        AND order_id = :orderId
    ) """, nativeQuery = true)
 boolean productOrderAlreadyExists(@Param("productId") Long productId, @Param("orderId") Long orderId);
}
