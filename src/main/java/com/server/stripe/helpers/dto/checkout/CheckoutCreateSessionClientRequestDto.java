package com.server.stripe.helpers.dto.checkout;

public class CheckoutCreateSessionClientRequestDto {

  public String productName;
  public String unitAmount;
  public String quantity;
  
  public CheckoutCreateSessionClientRequestDto(){}

  public CheckoutCreateSessionClientRequestDto(CheckoutCreateSessionClientRequestDto requestBody){
    this.productName = requestBody.productName;
    this.unitAmount = requestBody.unitAmount;
    this.quantity = requestBody.quantity;
  }

}
