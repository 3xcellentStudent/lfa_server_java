package com.server.config.api.exceptions.validation.cart;

import java.util.ArrayList;

import com.server.common.models.stripe.checkout.validation.cart.CartValidationErrorEntityDto;

public class CartValidationException extends RuntimeException {
  // Храним внутри ошибки список твоих DTO с конфликтами
  private final ArrayList<CartValidationErrorEntityDto> conflicts;

  public CartValidationException(ArrayList<CartValidationErrorEntityDto> conflicts) {
    super("Cart validation failed due to stock conflicts");
    this.conflicts = conflicts;
  }

  public ArrayList<CartValidationErrorEntityDto> getConflicts() {
    return conflicts;
  }

}