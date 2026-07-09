package com.server.common.models.stripe.checkout.validation.cart;

import org.springframework.data.annotation.Id;

import com.server.common.types.stripe.checkout.validation.cart.CartValidationErrorType;

public record CartValidationErrorEntityDto(
  @Id String productId, String collectionName, int requestedQuantity, int availableQuantity, CartValidationErrorType errorType
){}