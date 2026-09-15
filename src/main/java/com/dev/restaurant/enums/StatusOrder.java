package com.dev.restaurant.enums;

public enum StatusOrder {
  PENDING,
  DOING,
  DONE,
  DELIVERED,
  PAID,
  CANCELED;

  public boolean canBeAdd() {
    return this != PAID && this != CANCELED;
  }

  public boolean cantBeClosed() {
    return this != DELIVERED;
  }

}
