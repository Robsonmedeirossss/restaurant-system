package com.dev.restaurant.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dev.restaurant.entities.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

  
}
