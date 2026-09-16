package com.dev.restaurant.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.dev.restaurant.enums.PaymentType;
import com.dev.restaurant.enums.StatusPayment;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "payments")
public class Payment {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private BigDecimal value;

  @Enumerated(EnumType.STRING)
  private PaymentType paymentType;

  @Enumerated(EnumType.STRING)
  @Builder.Default private StatusPayment status = StatusPayment.PENDING;

  @Column(name = "external_transaction_code")
  private String externalTransactionCode;

  @Column(name = "payment_date")
  private LocalDateTime paymentDate;

  @CreationTimestamp
  @Column(name = "created_at")
  private LocalDateTime createdAt;

  @JoinColumn(name = "order_id")
  @OneToOne(fetch = FetchType.LAZY)
  private Order order;
}
