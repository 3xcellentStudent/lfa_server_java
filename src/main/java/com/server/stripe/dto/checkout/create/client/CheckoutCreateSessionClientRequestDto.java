package com.server.stripe.dto.checkout.create.client;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

public record CheckoutCreateSessionClientRequestDto(@NotBlank String productId, @PositiveOrZero int quantity){
}