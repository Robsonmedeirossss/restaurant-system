package com.dev.restaurant.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.dev.restaurant.entities.ProductOrder;
import com.dev.restaurant.enums.StatusProductOrder;

public interface ProductOrderRepository extends JpaRepository<ProductOrder, Long>, JpaSpecificationExecutor<ProductOrder>{   
    List<ProductOrder> findByStatus(StatusProductOrder status);
}