package com.dev.restaurant.repositories;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dev.restaurant.entities.Order;
import com.dev.restaurant.entities.ProductOrder;
import com.dev.restaurant.enums.StatusProductOrder;
import com.dev.restaurant.projections.OrderIndicatorsProjection;
import com.dev.restaurant.projections.OrdersByStatusProjection;

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

    @Query(value = """
            SELECT p
            FROM ProductOrder p
            WHERE (:status IS NULL OR p.status IN(:status))
            """)
    Page<ProductOrder> findAllProductsOrder(@Param("status") List<StatusProductOrder> status, Pageable pageable);

    @Query(value = """
        select 
        count(*) as "totalOrders",
        coalesce(sum(total_order), 0) as "totalRevenue",
        coalesce(round(avg(total_order) ,2), 0) as "averageOrderValue"
        from (
            select
        sum(po.quantity * po.unity_price) as total_order
        from orders o
        left join products_order po on po.order_id = o.id
        where o.status <> 'CANCELED'
        and(cast(:from as timestamp) is null or o.created_at >= :from)
        and(cast(:to as timestamp) is null or o.created_at <= :to)
        group by o.id
        ) as total_orders;
    """, nativeQuery = true)
    OrderIndicatorsProjection orderIndicadotors(@Param("from") LocalDate from, @Param("to") LocalDate to);

    @Query(value = """
        SELECT 
        o.status as status,
        count(o.id) as quantity
        from orders o
        where (cast(:from as timestamp) is null or o.created_at >= :from)
        and (cast(:to as timestamp) is null or o.created_at <= :to)
        group by o.status ;       
    """, nativeQuery = true)
    List<OrdersByStatusProjection> getTotalOrdersByStatus(@Param("from") LocalDate from, @Param("to") LocalDate to);
}
