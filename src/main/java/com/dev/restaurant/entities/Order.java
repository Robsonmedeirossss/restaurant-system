package com.dev.restaurant.entities;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.dev.restaurant.enums.StatusOrder;

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
import lombok.Builder;
import lombok.Data;

@Entity
@Data
@Builder
@Table(name = "orders")
public class Order {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Enumerated(EnumType.STRING)
  @Builder.Default private StatusOrder status = StatusOrder.PENDING;

  @JoinColumn(name = "table_id")
  @OneToOne(fetch = FetchType.LAZY)
  private RestaurantTable table;

  @Column(name = "opening_date")
  private LocalDateTime openingDate;

  @Column(name = "closing_date")
  private LocalDateTime closingDate;

  private String observation;

  @Column(name = "created_at")
  @CreationTimestamp
  private LocalDateTime createdAt;
}
