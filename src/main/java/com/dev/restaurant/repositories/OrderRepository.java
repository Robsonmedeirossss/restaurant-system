package com.dev.restaurant.repositories;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dev.restaurant.entities.Order;
import com.dev.restaurant.entities.ProductOrder;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
  @Query(value = """
        select 
            coalesce(sum(order_product.quantity * order_product.unity_price), 0) as subtotal
        from orders o
        left join products_order order_product
        on o.id = order_product.order_id 
        where o.id = :orderId AND order_product.status <> 'CANCELED'
        group by o.id;      
    """, nativeQuery = true)
    BigDecimal getSubtotal(@Param("orderId") Long orderId);

    @Query(value = """
            SELECT EXISTS(
                SELECT 1
                FROM products_order
                WHERE order_id = :orderId
                AND product_id = :productId
            );
            """ ,nativeQuery = true)
    boolean productOrderAlreadyExists(
        @Param("orderId") Long orderId,
        @Param("productId") Long productId
    );

    @Query(value = """
                SELECT *
                FROM products_order
                WHERE order_id = :orderId;
            """, nativeQuery = true)
    List<ProductOrder> findProductsOrderByOrderId(@Param("orderId") Long orderId);
}
