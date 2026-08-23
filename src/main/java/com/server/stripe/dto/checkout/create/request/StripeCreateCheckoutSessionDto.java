package com.server.stripe.dto.checkout.create.request;

import com.server.databases.mongodb.models.product.variation.ProductVariationModel.VariationOptions;

public record StripeCreateCheckoutSessionDto(
  String id, 
  String parentId, 
  int quantity, 
  long priceInCents, 
  String currency, 
  String variationName, 
  VariationOptions variationOptions, 
  String collection, 
  long createdAt, 
  long updatedAt
){}
