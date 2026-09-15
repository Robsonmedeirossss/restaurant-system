package com.dev.restaurant.enums;

import java.util.Set;

public enum StatusProductOrder {
  PENDING,
  DOING,
  DONE,
  DELIVERED,
  CANCELED;

  private Set<StatusProductOrder> allowedTransictions;

  static {
    PENDING.allowedTransictions = Set.of(DOING, CANCELED);
    DOING.allowedTransictions = Set.of(DONE, CANCELED);
    DONE.allowedTransictions = Set.of(DELIVERED);
    DELIVERED.allowedTransictions = Set.of();
    CANCELED.allowedTransictions = Set.of(); 
  }

  public boolean canTransiction(StatusProductOrder status) {
    return this.allowedTransictions.contains(status);
  }

  public boolean isDoing () {
    return this == DOING;
  }

  public boolean isDone () {
    return this == DONE;
  }

  public boolean isDelivered() {
    return this == DELIVERED;
  }

}
