package com.dev.restaurant.enums;

public enum StatusOrder {
  PENDING,
  DOING,
  DONE,
  CANCELED;

  public boolean canBeAdd() {
    return this == PENDING || this == DOING;
  }

  public boolean cantBeClosed() {
    return this == DOING;
  }

}
