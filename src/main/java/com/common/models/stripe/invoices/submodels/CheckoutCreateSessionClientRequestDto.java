package com.common.models.stripe.invoices.submodels;


import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

public class CheckoutCreateSessionClientRequestDto {

    @JsonProperty public @NotBlank String productId;
    @JsonProperty public @PositiveOrZero int quantity;
    @JsonProperty public @NotBlank String collectionName;

  public CheckoutCreateSessionClientRequestDto(){}

}