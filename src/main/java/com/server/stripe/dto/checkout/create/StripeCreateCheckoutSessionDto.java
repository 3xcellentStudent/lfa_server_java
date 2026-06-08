package com.server.stripe.dto.checkout.create;

import org.springframework.beans.factory.annotation.Value;

import com.server.databases.mongodb.models.product.variation.ProductVariationModel;
import com.server.databases.mongodb.models.product.variation.ProductVariationModel.VariationOptions;
import com.server.stripe.dto.checkout.create.client.CheckoutCreateSessionClientRequestDto;

public class StripeCreateCheckoutSessionDto {

  @Value("stripe.checkout.status.open")
  private String checkoutStatusOpen;
  
  public String id;
  public String parentId;
  public int quantity;
  public long priceInCents;
  public String currency;
  public String variationName;
  public VariationOptions variationOptions;
  public String collectionName;
  public long createdAt;
  public long updatedAt;

  public StripeCreateCheckoutSessionDto(){}

  public StripeCreateCheckoutSessionDto(CheckoutCreateSessionClientRequestDto entity, ProductVariationModel productVariation){
    this.id = entity.productId;
    this.parentId = productVariation.getParentId();
    this.quantity = entity.quantity;
    this.priceInCents = productVariation.getStockInfo().getPriceInCents();
    this.currency = productVariation.getStockInfo().getCurrency();
    this.variationName = productVariation.getVariationName();
    this.variationOptions = productVariation.getVariationOptions();
    this.collectionName = productVariation.getCollectionName();
    this.createdAt = productVariation.getCreatedAt();
    this.updatedAt = productVariation.getUpdatedAt();
  }
}
