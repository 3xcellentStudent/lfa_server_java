package com.server.config.api.exceptions.validation.cart;

import java.util.List;

import org.springframework.data.annotation.Id;

public class CartValidationException extends RuntimeException {
    // Храним внутри ошибки список твоих DTO с конфликтами
    private final List<CartValidationErrorEntityDto> conflicts;

    public CartValidationException(List<CartValidationErrorEntityDto> conflicts) {
        super("Cart validation failed due to stock conflicts");
        this.conflicts = conflicts;
    }

    public List<CartValidationErrorEntityDto> getConflicts() {
        return conflicts;
    }


  public record CartValidationErrorEntityDto(@Id String productId, String collectionName, int requestedQuantity, int availableQuantity){
  }
}