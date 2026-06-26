package com.dev.restaurant.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dev.restaurant.entities.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

  
}
