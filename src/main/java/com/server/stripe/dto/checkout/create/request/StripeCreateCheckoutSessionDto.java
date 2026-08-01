package com.server.stripe.dto.checkout.create.request;

import com.server.databases.mongodb.models.product.variation.ProductVariationModel;
import com.server.databases.mongodb.models.product.variation.ProductVariationModel.VariationOptions;
import com.server.stripe.dto.checkout.create.client.CheckoutCreateSessionClientRequestDto;

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
){

  // private String id;
  // private String parentId;
  // private int quantity;
  // private long priceInCents;
  // private String currency;
  // private String variationName;
  // private VariationOptions variationOptions;
  // private String collection;
  // private long createdAt;
  // private long updatedAt;

  // public StripeCreateCheckoutSessionDto(){}

  // public StripeCreateCheckoutSessionDto(CheckoutCreateSessionClientRequestDto entity, ProductVariationModel productVariation, String collection){
  //   this.id = entity.productId();
  //   this.parentId = productVariation.getParentId();
  //   this.quantity = entity.quantity();
  //   this.priceInCents = productVariation.getStockInfo().getPriceInCents();
  //   this.currency = productVariation.getStockInfo().getCurrency();
  //   this.variationName = productVariation.getVariationName();
  //   this.variationOptions = productVariation.getVariationOptions();
  //   this.collection = collection;
  //   this.createdAt = productVariation.getCreatedAt();
  //   this.updatedAt = productVariation.getUpdatedAt();
  // }
}
