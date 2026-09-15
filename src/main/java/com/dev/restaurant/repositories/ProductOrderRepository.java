package com.dev.restaurant.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dev.restaurant.entities.ProductOrder;
import com.dev.restaurant.enums.StatusProductOrder;


public interface ProductOrderRepository extends JpaRepository<ProductOrder, Long>, JpaSpecificationExecutor<ProductOrder>{   
    List<ProductOrder> findByStatus(StatusProductOrder status);

    @Query(value = """
        SELECT EXISTS (
            SELECT 1
            FROM products_order po
            where po.id = :productOrderId and po.order_id = :orderId
        );
    """, nativeQuery = true)
    boolean checkIfOrderAndProductOrderExist(
        @Param("productOrderId") Long productOrderId,
        @Param("orderId")  Long orderId
    );

    @Query(value = """
        select
        coalesce(sum(po.quantity), 0) as "totalProductsSold"
        from orders o
        inner join products_order po on po.order_id = o.id
        where o.status ='PAID';
    """, nativeQuery = true)
    Long getTotalProductsSold();
}