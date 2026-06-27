package com.dev.restaurant.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "billings")
public class Billing {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Integer discount;

  @Column(name = "service_tax")
  private Integer serviceTax;
  private BigDecimal subtotal;
  private BigDecimal total;
  
  @Column(name = "created_at")
  @CreationTimestamp
  private LocalDateTime createdAt;

  @Column(name = "closed_at")
  private LocalDateTime closedAt;

  @JoinColumn(name = "order_id")
  @OneToOne(fetch = FetchType.LAZY)
  private Order order;
}
