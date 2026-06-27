package com.dev.restaurant.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.dev.restaurant.enums.StatusProductOrder;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "products_orders")
public class ProductOrder {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "unity_price")
  private BigDecimal unityPrice;

  private Integer quantity;

  private String observation;

  @Enumerated(EnumType.STRING)
  @Builder.Default private StatusProductOrder status = StatusProductOrder.PENDING;

  @Column(name = "preparation_start_at")
  private LocalDateTime preparationStartAt;

  @Column(name = "preparation_end_at")
  private LocalDateTime preparationEndAt;

  @Column(name = "created_at")
  @CreationTimestamp
  private LocalDateTime createdAt;

  @JoinColumn(name = "product_id")
  @ManyToOne(fetch = FetchType.LAZY)
  private Product product;

  @JoinColumn(name = "order_id")
  @ManyToOne(fetch = FetchType.LAZY)
  private Order order;
}
