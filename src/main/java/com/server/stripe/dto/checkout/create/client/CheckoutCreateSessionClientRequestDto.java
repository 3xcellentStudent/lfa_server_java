package com.server.stripe.dto.checkout.create.client;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

public record CheckoutCreateSessionClientRequestDto(@NotBlank String productId, @PositiveOrZero int quantity, @NotBlank String collectionName){

    // @JsonProperty public @NotBlank String productId;
    // @JsonProperty public @PositiveOrZero int quantity;
    // @JsonProperty public @NotBlank String collectionName;

  // public CheckoutCreateSessionClientRequestDto(){}

}