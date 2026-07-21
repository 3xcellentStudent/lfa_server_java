package com.server.common.types.stripe.orders;

public enum OrdersProcessingType {
  CREATED,
  PENDING,
  PROCESSING,
  ASSEMBLING,
  PACKED,
  SHIPPED,
  DELIVERED,
  COMPLETED,
  CANCELLED,
  REFUNDED
}