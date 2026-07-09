package com.server.common.api.exceptions.validation.cart;

import java.util.ArrayList;

import com.server.common.models.stripe.checkout.validation.cart.CartValidationErrorEntityDto;

public class CartValidationException extends RuntimeException {

  private final ArrayList<CartValidationErrorEntityDto> conflicts;

  public CartValidationException(ArrayList<CartValidationErrorEntityDto> conflicts) {
    super("Some items in your cart are out of stock or unavailable.");
    this.conflicts = conflicts;
  }

  public ArrayList<CartValidationErrorEntityDto> getConflicts() {
    return conflicts;
  }

}