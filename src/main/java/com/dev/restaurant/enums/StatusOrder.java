package com.dev.restaurant.enums;

public enum StatusOrder {
  PENDING,
  DOING,
  DONE,
  DELIVERED,
  CANCELED;

  public boolean canBeAdd() {
    return this == PENDING || this == DOING;
  }
}
