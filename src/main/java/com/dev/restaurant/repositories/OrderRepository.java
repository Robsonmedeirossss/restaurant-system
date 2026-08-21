package com.dev.restaurant.repositories;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dev.restaurant.entities.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
  @Query(value = """
        select 
            coalesce(sum(order_product.quantity * order_product.unity_price), 0) as subtotal
        from orders o
        left join products_order order_product
        on o.id = order_product.order_id 
        where o.id = :orderId
        group by o.id;      
    """, nativeQuery = true)
    BigDecimal getSubtotal(@Param("orderId") Long orderId);
}
