package com.dev.restaurant.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dev.restaurant.entities.Product;
import com.dev.restaurant.projections.PreparationIndicatorsProjection;
import com.dev.restaurant.projections.TopProductsProjection;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(value = """
        select
        p.name as "productName",
        round(
            avg(
                extract(
                    epoch from (po.preparation_end_at - po.preparation_start_at)
                ) / 60
            )
        , 2) as "averagePreparationTime"
        from products p 
        inner join products_order po on p.id = po.product_id
        group by p.id, p.name;
    """, nativeQuery = true)
    List<PreparationIndicatorsProjection> getAveragPreparationMinutes();

    @Query(value = """
        select 
            p.name as "productName",
            sum(quantity) as "quantitySold",
            round(sum(po.quantity * po.unity_price), 2) as "revenue"
        from products p
        inner join products_order po on po.product_id = p.id
        inner join orders o on o.id = po.order_id 
        where o.status = 'PAID'
            and(cast(:from as timestamp) is null or o.created_at >= :from)
            and(cast(:to as timestamp) is null or o.created_at <= :to)
        group by p.name, p.id
        order by sum(po.quantity * po.unity_price) desc
        limit 3;        
    """, nativeQuery = true)
    List<TopProductsProjection> getTopProducts(@Param("from") LocalDate from, @Param("to") LocalDate to);
  
}
