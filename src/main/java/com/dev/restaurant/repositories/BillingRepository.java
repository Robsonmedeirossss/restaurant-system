package com.dev.restaurant.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dev.restaurant.entities.Billing;

@Repository
public interface BillingRepository extends JpaRepository<Billing, Long> {
  
}
