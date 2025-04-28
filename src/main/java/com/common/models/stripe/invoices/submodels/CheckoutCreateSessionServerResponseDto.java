package com.common.models.stripe.invoices.submodels;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CheckoutCreateSessionServerResponseDto {
  private String id;
  
  @JsonProperty("client_secret")
  private String clientSecret;

  private Map<String, Object> metadata;
  @JsonProperty("amount_total")
  private Integer amountTotal;

  @JsonProperty("amount_subtotal")
  private Integer amountSubtotal;

  private long created;
  @JsonProperty("expires_at")
  private Long expiresAt;

  private String status;
  @JsonProperty("return_url")
  private String returnUrl;

  private String currency;
  @JsonProperty("payment_status")
  private String paymentStatus;

  public CheckoutCreateSessionServerResponseDto(){}

  public CheckoutCreateSessionServerResponseDto(StripeCheckoutSessionsDataModel requestBody){
    this.id = requestBody.getId();
    this.clientSecret = requestBody.getClientSecret();
    this.metadata = requestBody.getMetadata();
    this.amountTotal = requestBody.getAmountTotal();
    this.amountSubtotal = requestBody.getAmountSubtotal();
    this.created = requestBody.getCreated();
    this.expiresAt = requestBody.getExpiresAt();
    this.status = requestBody.getStatus();
    this.returnUrl = requestBody.getReturnUrl();
    this.currency = requestBody.getCurrency();
    this.paymentStatus = requestBody.getPaymentStatus();
  }

  public CheckoutCreateSessionServerResponseDto(CheckoutCreateSessionServerResponseDto requestBody){
    this.id = requestBody.id;
    this.clientSecret = requestBody.clientSecret;
    this.metadata = requestBody.metadata;
    this.amountTotal = requestBody.amountTotal;
    this.amountSubtotal = requestBody.amountSubtotal;
    this.created = requestBody.created;
    this.expiresAt = requestBody.expiresAt;
    this.status = requestBody.status;
    this.returnUrl = requestBody.returnUrl;
    this.currency = requestBody.currency;
    this.paymentStatus = requestBody.paymentStatus;
  }

  public String getId(){
    return this.id;
  }

  public String getClientSecret(){
    return this.clientSecret;
  }
}
