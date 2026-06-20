package com.dev.restaurant.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dev.restaurant.entities.Billing;

public interface BillingRepository extends JpaRepository<Billing, Long> {
  
}
