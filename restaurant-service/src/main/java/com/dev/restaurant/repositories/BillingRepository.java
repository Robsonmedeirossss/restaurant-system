package com.dev.restaurant.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dev.restaurant.entities.Billing;

@Repository
public interface BillingRepository extends JpaRepository<Billing, Long> {
  Optional<Billing> findByOrderId(Long orderId);
}
